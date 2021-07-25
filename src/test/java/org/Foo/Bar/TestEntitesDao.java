package org.Foo.Bar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Random;

import org.Foo.Bar.Entities.User;
import org.Foo.Bar.EntitiesDao.UserDao;
import org.Foo.Bar.Services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@SpringJUnitWebConfig(locations = "classpath:springmvc.xml", resourcePath = "src/main/resources")
public class TestEntitesDao {
  @Autowired
  private UserService userService;
  @Autowired
  private UserDao userDao;

  @Test
  public void testPersistUser() {
    User user = new User();
    user.setEmail("hello@world.com" + new Random().nextInt(5555));
    user.setName("bar");
    user.setPokeToken(0L);
    Integer id = userService.persistUser(user);
    User foundUser = userDao.findByEmail(user.getEmail());
    assertNotNull(foundUser);
    userDao.deleteById(id);
    foundUser = userDao.findByEmail(user.getEmail());
    assertNull(foundUser);
  }

  @Test
  public void testCreditTokensToUser() {
    User user = newUser();
    Long amntToCredit = 222L;
    userDao.updateUserToken(amntToCredit, user.getEmail());
    User u = userDao.findByEmail(user.getEmail());
    assertEquals(u.getPokeToken(), amntToCredit);
    userDao.deleteById(u.getId());
    assertNull(userDao.findByEmail(user.getEmail()));
  }

  public User newUser() {
    User user = new User();
    user.setEmail("hello@world.com" + new Random().nextInt(5555));
    user.setName("bar");
    user.setPokeToken(0L);
    user = userDao.save(user);
    return user;
  }
}
