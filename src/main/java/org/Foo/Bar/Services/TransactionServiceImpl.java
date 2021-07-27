package org.Foo.Bar.Services;

import java.util.Date;

import javax.transaction.Transactional;

import org.Foo.Bar.Entities.PokeTransaction;
import org.Foo.Bar.Entities.TokenTxLog;
import org.Foo.Bar.Entities.User;
import org.Foo.Bar.EntitiesDao.PokeTransactionDao;
import org.Foo.Bar.EntitiesDao.TokenTxLogDao;
import org.Foo.Bar.EntitiesDao.UserDao;
import org.Foo.Bar.Exceptions.InsufficentTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
  @Autowired
  private TokenTxLogDao tokenTxLogDao;
  @Autowired
  private UserDao userDao;
  @Autowired
  private PokeTransactionDao pokeTransactionDao;

  private void checkSufficientBalance(User customer, Long amnt) {
    if (customer.getPokeToken() < amnt) {
      log.warn("{}, who has {}, is trying to complete a transaction ({})", customer.getEmail(), customer.getPokeToken(),
          amnt);
      throw new InsufficentTokenException();
    }
  }

  @Override
  @Transactional
  public TokenTxLog triggerTransaction(PokeTransaction tx, String email) {
    User customer = userDao.findByEmail(email);
    tx.setTxCreatedTime(new Date());
    checkSufficientBalance(customer, tx.getPokeTokenExchanged());
    userDao.updateUserTokenByDelta(-tx.getPokeTokenExchanged(), email);
    TokenTxLog tokenTxLog = new TokenTxLog();
    tokenTxLog.setCustomer(customer);
    tokenTxLog.setTransaction(tx);
    pokeTransactionDao.save(tx);
    log.info("{} completed a transaction ({})", customer.getEmail(), tx.getPokeTokenExchanged());
    return tokenTxLogDao.save(tokenTxLog);
  }
}
