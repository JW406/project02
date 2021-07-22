package org.Foo.Bar.Services;

import org.Foo.Bar.Entities.User;
import org.Foo.Bar.EntitiesDao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserDao userDao;

  @Override
  @Transactional
  public void persistUser(User user) {
    if (userDao.findByEmail(user.getEmail()) == null) {
      userDao.save(user);
    }
  }
}
