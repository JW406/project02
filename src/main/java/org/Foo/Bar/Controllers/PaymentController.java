package org.Foo.Bar.Controllers;

import javax.servlet.http.HttpServletRequest;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;

import org.Foo.Bar.SpringContextAccessor;
import org.Foo.Bar.EntitiesDao.UserDao;
import org.Foo.Bar.Exceptions.InvalidAuthorizationHeader;
import org.Foo.Bar.RestObjects.CheckoutModel;
import org.Foo.Bar.Security.TokenManager;
import org.Foo.Bar.UtilityServices.HTTPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PaymentController {
  private HTTPUtils http;
  private UserDao userDao;
  private TokenManager tokenManager;

  @Autowired
  public PaymentController(HTTPUtils http, UserDao userDao, TokenManager tokenManager) {
    this.http = http;
    this.userDao = userDao;
    this.tokenManager = tokenManager;
  }

  @GetMapping("/pay-cb/success")
  public String success() {
    return "stripe_success";
  }

  @ResponseBody
  @GetMapping("/pay/success-hook")
  public ResponseEntity<?> successHook(HttpServletRequest req, @RequestHeader HttpHeaders headers) {
    Long pokeTokenQuantities = (Long) req.getSession().getAttribute("pokeToken");
    String email;
    try {
      email = tokenManager.getUsernameFromHeader(headers);
    } catch (InvalidAuthorizationHeader e) {
      return ResponseEntity.status(401).body(null);
    }
    userDao.updateUserTokenByDelta(pokeTokenQuantities, email);
    log.info("User payment successful");
    return ResponseEntity.ok().body(null);
  }

  @GetMapping("/pay-cb/cancel")
  public String cancel() {
    log.info("User payment canceled");
    return "stripe_cancel";
  }

  @PostMapping("/pay/create-checkout-session")
  public String checkOut(HttpServletRequest req, @ModelAttribute CheckoutModel param) throws StripeException {
    log.info("Creating User checkout out session...");
    Stripe.apiKey = SpringContextAccessor.stripeClientSecret;
    String baseUrl = http.normalizeRemoteHost(req.getScheme(), req.getRemoteHost(), req.getServerPort());
    LineItem item = SessionCreateParams.LineItem.builder() //
        .setQuantity(param.getQuantities()) //
        .setPriceData( //
            SessionCreateParams.LineItem.PriceData.builder() //
                .setCurrency("usd") //
                .setUnitAmount(param.getAmount()) //
                .setProductData( //
                    SessionCreateParams.LineItem.PriceData.ProductData.builder() //
                        .setName(param.getItemName()).build()) //
                .build()) //
        .build();
    SessionCreateParams params = SessionCreateParams.builder() //
        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD) //
        .setMode(SessionCreateParams.Mode.PAYMENT) //
        .setSuccessUrl(baseUrl + "/pay-cb/success") //
        .setCancelUrl(baseUrl + "/pay-cb/cancel") //
        .addLineItem(item) //
        .build();

    // TODO: avoid session and look for other solutions. The goal to to share some
    // states among different server end points.
    req.getSession().setAttribute("pokeToken", param.getQuantities());
    Session session = Session.create(params);
    return "redirect:" + session.getUrl();
  }
}
