package org.Foo.Bar.EntitiesDao;

import javax.transaction.Transactional;

import org.Foo.Bar.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
  @Query("from User where email = ?1")
  User findByEmail(String email);

  @Query("select u.pokeToken from User u where u.email = ?1")
  Long queryUserPokeToken(String email);

  @Modifying
  @Transactional
  @Query("update User u set u.pokeToken = u.pokeToken + ?1 where u.email = ?2")
  void updateUserTokenByDelta(Long pokeToken, String email);

  @Modifying
  @Transactional
  @Query("update User u set u.name = ?2, u.zipCode = ?3 where u.email = ?1")
  void updateUserNameAndZipCode(String email, String name, String zipCode);
}
