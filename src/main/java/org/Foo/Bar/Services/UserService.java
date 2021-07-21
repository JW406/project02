package org.Foo.Bar.Services;

import org.Foo.Bar.Entities.User;

public interface UserService {
  void persistUser(User user);
  List<User> getAllUsers();
  void addUser(User user);
  User getUserById(Integer id);
}
