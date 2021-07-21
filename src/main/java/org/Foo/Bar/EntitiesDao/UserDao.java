package org.Foo.Bar.EntitiesDao;

import org.Foo.Bar.Entities.User;

public interface UserDao {
  void insertUser(User user);
  User findUserByEmail(String email);
  User getUserById(Integer id);
  void addUser(User user);
  void deleteUser(Integer id);
  List<User> getAllUsers();
  
}
