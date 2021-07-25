package org.Foo.Bar.EntitiesDao;

import org.Foo.Bar.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
  @Query("from User where email = ?1")
  User findByEmail(String email);

  @Query("select u.pokeToken from User u where u.email = ?1")
  Long queryUserPokeToken(String email);

  @Modifying
  @Transactional
  @Query("update User u set u.pokeToken = u.pokeToken + ?1 where u.email = ?2")
  void updateUserToken(Long pokeToken, String email);
}
