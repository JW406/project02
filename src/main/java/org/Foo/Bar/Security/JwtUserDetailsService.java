package org.Foo.Bar.Security;

import java.util.ArrayList;

import org.Foo.Bar.EntitiesDao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
  @Autowired
  private UserDao userDao;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    org.Foo.Bar.Entities.User user = userDao.findByEmail(email);
    if (user != null) {
      return new User(user.getEmail(), "", new ArrayList<>());
    } else {
      throw new UsernameNotFoundException("User not found with email: " + email);
    }
  }
}
