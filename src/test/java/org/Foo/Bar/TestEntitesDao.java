package org.Foo.Bar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.Foo.Bar.Entities.PokeTransaction;
import org.Foo.Bar.Entities.PokemonItem;
import org.Foo.Bar.Entities.TokenTxLog;
import org.Foo.Bar.Entities.TxType;
import org.Foo.Bar.Entities.User;
import org.Foo.Bar.EntitiesDao.PokeItemDao;
import org.Foo.Bar.EntitiesDao.TokenTxLogDao;
import org.Foo.Bar.EntitiesDao.UserDao;
import org.Foo.Bar.Exceptions.InsufficentTokenException;
import org.Foo.Bar.Services.TransactionService;
import org.Foo.Bar.Services.UserService;
import org.Foo.Bar.UtilityServices.HTTPUtils;
import org.Foo.Bar.UtilityServices.QueryStringParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@SpringJUnitWebConfig(locations = "classpath:springmvc.xml", resourcePath = "src/main/resources")
public class TestEntitesDao {
  @Autowired
  private UserService userService;
  @Autowired
  private UserDao userDao;
  @Autowired
  private PokeItemDao pokeItemDao;
  @Autowired
  private TransactionService transactionService;
  @Autowired
  private TokenTxLogDao tokenTxLogDao;

  private User newUser() {
    User user = new User();
    user.setEmail("hello@world.com" + new Random().nextInt(5555) + 9999);
    user.setName("bar");
    user.setPokeToken(1000L);
    user = userDao.save(user);
    return user;
  }

  private void deleteUser(User user) {
    userDao.deleteById(user.getId());
  }

  @Test
  public void testGetBean() {
    Object res = SpringContextAccessor.getBean("dataSource");
    assertNotNull(res);
    ComboPooledDataSource res2 = SpringContextAccessor.getBean("dataSource", ComboPooledDataSource.class);
    assertNotNull(res2);
  }

  @Autowired
  private HTTPUtils http;

  @Test
  public void testHttpUtils() {
    assertEquals(http.normalizeRemoteHost("http", "127.0.0.1", 8080), "http://localhost:8080");
    String qs = http.encodeQueryString(new TreeMap<String, String>() {
      {
        put("abc", "bar");
        put("qwf", "foo");
      }
    });
    assertEquals(qs, "abc=bar&qwf=foo");
  }

  @Autowired
  private QueryStringParser qsParser;

  @Test
  public void testQsParser() {
    Map<String, String> res = qsParser.parse("abc=qwf&luy=nei");
    assertEquals(res.get("abc"), "qwf");
    assertEquals(res.get("luy"), "nei");
  }

  @Test
  public void testPersistUser() {
    User user = new User();
    user.setEmail("hello@world.com" + new Random().nextInt(5555) + 19999);
    userService.persistUser(user);
    User foundUser = userDao.findByEmail(user.getEmail());
    assertNotNull(foundUser);
    deleteUser(foundUser);
    foundUser = userDao.findByEmail(user.getEmail());
    assertNull(foundUser);
  }

  @Test
  public void testCreditTokensToUser() {
    User user = newUser();
    Long amntToCredit = 222L;
    Long pokeTokenBefore = user.getPokeToken();
    userDao.updateUserTokenByDelta(amntToCredit, user.getEmail());
    User u = userDao.findByEmail(user.getEmail());
    assertEquals(u.getPokeToken(), pokeTokenBefore + amntToCredit);
    deleteUser(user);
    assertNull(userDao.findByEmail(user.getEmail()));
  }

  @Test
  public void testInsertOnePokeItem() {
    PokemonItem pokemonItem = new PokemonItem();
    pokemonItem.setPokeName("helloooo");
    pokemonItem = pokeItemDao.save(pokemonItem);
    assertNotNull(pokemonItem);
    pokeItemDao.deleteById(pokemonItem.getId());
  }

  @Test
  public void testTransaction() {
    User user = newUser();

    Long pokeTokenBefore = user.getPokeToken();
    PokeTransaction tx = new PokeTransaction();
    tx.setTxSourceType(TxType.Order);
    tx.setPokeTokenExchanged(Math.abs(new Random().nextLong() % 1000));
    TokenTxLog txLog = transactionService.triggerTransaction(tx, user.getEmail());
    user = userDao.findByEmail(user.getEmail());
    assertEquals(pokeTokenBefore - tx.getPokeTokenExchanged(), user.getPokeToken());

    tokenTxLogDao.deleteById(txLog.getId());
    deleteUser(user);
  }

  @Test
  public void testTransactionOverdraft() {
    final User user = newUser();

    Long pokeTokenBefore = user.getPokeToken();
    PokeTransaction tx = new PokeTransaction();
    tx.setTxSourceType(TxType.Order);
    tx.setPokeTokenExchanged(pokeTokenBefore + 999);
    assertThrows(InsufficentTokenException.class, () -> {
      transactionService.triggerTransaction(tx, user.getEmail());
    });
    User userRefetch = userDao.findByEmail(user.getEmail());
    assertEquals(pokeTokenBefore, userRefetch.getPokeToken());
    deleteUser(userRefetch);
  }

  @Test
  public void testUpdateUserNameAndZipCode() {
    User user = newUser();

    String newName = "Foobar";
    String newZipCode = "33065";
    userDao.updateUserNameAndZipCode(user.getEmail(), newName, newZipCode);

    user = userDao.findByEmail(user.getEmail());

    assertEquals(newName, user.getName());
    assertEquals(newZipCode, user.getZipCode());

    deleteUser(user);
  }
}
