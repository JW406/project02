package org.Foo.Bar;

import java.util.Random;

import org.Foo.Bar.Entities.User;
import org.Foo.Bar.EntitiesDao.UserDao;

public class TestUtils {
  public static User newUser(UserDao userDao) {
    User user = new User();
    user.setEmail("hello@world.com" + new Random().nextInt(5555) + 9999);
    user.setName("bar");
    user.setPokeToken(1000L);
    user = userDao.save(user);
    return user;
  }

  public static void deleteUser(UserDao userDao, User user) {
    userDao.deleteById(user.getId());
  }
}
