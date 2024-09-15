package org.Foo.Bar.Services;

import java.util.Date;

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
  public Integer persistUser(User user) {
    Integer res = null;
    if (userDao.findByEmail(user.getEmail()) == null) {
      user.setPokeToken(0L);
      user.setTsDateRegistered(new Date());
      User u = userDao.save(user);
      res = u.getId();
    }
    return res;
  }
  @Override
  @Transactional
  public List<User> getAllUsers() {
    return userDao.getAllUsers();
  }

  @Override
  @Transactional
  public void addUser(User user) {
    userDao.addUser(user);
  }

  @Override
  public User getUserById(Integer id) {
    return userDao.getUserById(id);
  }
  
}
