package org.Foo.Bar.Controllers;

import javax.servlet.http.HttpServletRequest;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;

import org.Foo.Bar.SpringContextAccessor;
import org.Foo.Bar.RestObjects.CheckoutModel;
import org.Foo.Bar.UtilityServices.HTTPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PaymentController {
  private HTTPUtils http;

  @Autowired
  public PaymentController(HTTPUtils http) {
    this.http = http;
  }

  @GetMapping("/pay/success")
  public String success() {
    return "stripe_success";
  }

  @GetMapping("/pay/cancel")
  public String cancel() {
    return "stripe_cancel";
  }

  @PostMapping("/pay/create-checkout-session")
  public String checkOut(HttpServletRequest req, @ModelAttribute CheckoutModel param) throws StripeException {
    Stripe.apiKey = SpringContextAccessor.stripeClientSecret;
    String baseUrl = http.normalizeRemoteHost(req);
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
        .setSuccessUrl(baseUrl + "/pay/success") //
        .setCancelUrl(baseUrl + "/pay/cancel") //
        .addLineItem(item) //
        .build();

    Session session = Session.create(params);
    return "redirect:" + session.getUrl();
  }
}
