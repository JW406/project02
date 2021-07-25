package org.Foo.Bar.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.Foo.Bar.Entities.User;
import org.Foo.Bar.EntitiesDao.UserDao;
import org.Foo.Bar.Security.TokenManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestUtilController {
  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private UserDao userDao;

  @Autowired
  private TokenManager tokenManager;

  @GetMapping("/api/foo")
  public Map<Object, Object> hello() {
    Session sess = sessionFactory.openSession();
    Map<Object, Object> a = new HashMap<>();

    List<User> list = sess.createQuery("select u from User u").list();
    for (int i = 0; i < list.size(); i++) {
      User u = list.get(i);
      a.put(u.getEmail(), u.getName());
    }

    sess.close();
    return a;
  }

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
}
