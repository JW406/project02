package org.Foo.Bar.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.Foo.Bar.Entities.PokeTransaction;
import org.Foo.Bar.Entities.TxType;
import org.Foo.Bar.Entities.User;
import org.Foo.Bar.EntitiesDao.PokeItemDao;
import org.Foo.Bar.EntitiesDao.UserDao;
import org.Foo.Bar.RestObjects.MakeTransactionBody;
import org.Foo.Bar.RestObjects.UpdateUserInfo;
import org.Foo.Bar.RestObjects.SuccessResponse;
import org.Foo.Bar.Security.TokenManager;
import org.Foo.Bar.Services.TransactionService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestUtilController {
  @Autowired
  private SessionFactory sessionFactory;
  @Autowired
  private UserDao userDao;
  @Autowired
  private PokeItemDao pokeItemDao;
  @Autowired
  private TokenManager tokenManager;
  @Autowired
  private TransactionService transactionService;

  @GetMapping("/api/user/tokens")
  public Map<Object, Object> userTokenQuery(@RequestHeader HttpHeaders headers) {
    String email = tokenManager.getUsernameFromHeader(headers);
    Long tokens = userDao.queryUserPokeToken(email);
    return new HashMap<Object, Object>() {
      {
        put("email", email);
        put("pokeToken", tokens);
      }
    };
  }

  @GetMapping("/api/shop/get-all-pokemons")
  public List<?> getAllPokemons() {
    return pokeItemDao.findAll();
  }

  @GetMapping("/api/get-user-info")
  public User getUserInfo(@RequestHeader HttpHeaders headers) {
    String email = tokenManager.getUsernameFromHeader(headers);
    return userDao.findByEmail(email);
  }

  @PatchMapping("/api/update-user-info")
  public SuccessResponse updateUserInfo(@RequestHeader HttpHeaders headers, @RequestBody UpdateUserInfo body) {
    String email = tokenManager.getUsernameFromHeader(headers);
    userDao.updateUserNameAndZipCode(email, body.getName(), body.getZipCode());
    SuccessResponse successResponse = new SuccessResponse();
    successResponse.setSuccess(true);
    return successResponse;
  }

  @PostMapping("/api/shop/make-transaction")
  public SuccessResponse makeTransaction(@RequestBody MakeTransactionBody body, @RequestHeader HttpHeaders headers) {
    String email = tokenManager.getUsernameFromHeader(headers);
    PokeTransaction tx = new PokeTransaction();
    tx.setPokeTokenExchanged(body.getTxAmnt());
    tx.setTxSourceType(TxType.Order);
    SuccessResponse successResponse = new SuccessResponse();
    try {
      transactionService.triggerTransaction(tx, email);
      successResponse.setSuccess(true);
    } catch (Exception e) {
      successResponse.setSuccess(false);
    }
    return successResponse;
  }
}
