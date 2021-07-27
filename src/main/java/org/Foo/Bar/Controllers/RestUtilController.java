package org.Foo.Bar.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.Foo.Bar.Entities.PokeTransaction;
import org.Foo.Bar.Entities.TokenTxLog;
import org.Foo.Bar.Entities.TxType;
import org.Foo.Bar.Entities.User;
import org.Foo.Bar.EntitiesDao.PokeItemDao;
import org.Foo.Bar.EntitiesDao.UserDao;
import org.Foo.Bar.Exceptions.InvalidAuthorizationHeader;
import org.Foo.Bar.RestObjects.MakeTransactionBody;
import org.Foo.Bar.RestObjects.SuccessResponse;
import org.Foo.Bar.RestObjects.TokenQueryResponse;
import org.Foo.Bar.RestObjects.TransactionResponse;
import org.Foo.Bar.RestObjects.UpdateUserInfo;
import org.Foo.Bar.Security.TokenManager;
import org.Foo.Bar.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestUtilController {
  @Autowired
  private UserDao userDao;
  @Autowired
  private PokeItemDao pokeItemDao;
  @Autowired
  private TokenManager tokenManager;
  @Autowired
  private TransactionService transactionService;

  @GetMapping("/api/user/tokens")
  public ResponseEntity<TokenQueryResponse> userTokenQuery(@RequestHeader HttpHeaders headers) {
    String email;
    TokenQueryResponse tokenQueryResponse = new TokenQueryResponse();
    try {
      email = tokenManager.getUsernameFromHeader(headers);
    } catch (InvalidAuthorizationHeader e) {
      return ResponseEntity.status(401).body(tokenQueryResponse);
    }
    Long tokens = userDao.queryUserPokeToken(email);
    tokenQueryResponse.setEmail(email);
    tokenQueryResponse.setPokeToken(tokens);
    return ResponseEntity.ok(tokenQueryResponse);
  }

  @GetMapping("/api/shop/get-all-pokemons")
  public List<?> getAllPokemons() {
    return pokeItemDao.findAll();
  }

  @GetMapping("/api/get-user-info")
  public ResponseEntity<User> getUserInfo(@RequestHeader HttpHeaders headers) {
    String email;
    try {
      email = tokenManager.getUsernameFromHeader(headers);
    } catch (InvalidAuthorizationHeader e) {
      return ResponseEntity.status(401).body(null);
    }
    return ResponseEntity.ok(userDao.findByEmail(email));
  }

  @PatchMapping("/api/update-user-info")
  public ResponseEntity<SuccessResponse> updateUserInfo(@RequestHeader HttpHeaders headers,
      @RequestBody UpdateUserInfo body) {
    String email;
    try {
      email = tokenManager.getUsernameFromHeader(headers);
    } catch (InvalidAuthorizationHeader e) {
      return ResponseEntity.status(401).body(null);
    }
    userDao.updateUserNameAndZipCode(email, body.getName(), body.getZipCode());
    SuccessResponse successResponse = new SuccessResponse();
    successResponse.setSuccess(true);
    return ResponseEntity.ok(successResponse);
  }

  @PostMapping("/api/shop/make-transaction")
  public ResponseEntity<TransactionResponse> makeTransaction(@RequestBody MakeTransactionBody body,
      @RequestHeader HttpHeaders headers) {
    String email;
    try {
      email = tokenManager.getUsernameFromHeader(headers);
    } catch (InvalidAuthorizationHeader e1) {
      return ResponseEntity.status(401).body(null);
    }
    PokeTransaction tx = new PokeTransaction();
    tx.setPokeTokenExchanged(body.getTxAmnt());
    tx.setTxSourceType(TxType.Order);
    TransactionResponse txResponse = new TransactionResponse();
    try {
      TokenTxLog triggerTransaction = transactionService.triggerTransaction(tx, email);
      txResponse.setSuccess(true);
      txResponse.setTxId(triggerTransaction.getId());
    } catch (Exception e) {
      txResponse.setSuccess(false);
      txResponse.setTxId(-1);
    }
    return ResponseEntity.ok(txResponse);
  }
}
