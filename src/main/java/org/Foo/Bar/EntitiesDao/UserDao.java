package org.Foo.Bar.EntitiesDao;

import org.Foo.Bar.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
  @Query("from User where email = ?1")
  User findByEmail(String email);
}
