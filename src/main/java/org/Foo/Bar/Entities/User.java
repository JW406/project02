package org.Foo.Bar.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "proj02_User")
public class User {
  @Setter(value = AccessLevel.NONE)
  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJ02_USER")
  @SequenceGenerator(name = "PROJ02_USER", sequenceName = "PROJ02_USER_S", allocationSize = 1)
  @Column
  private Integer id;

  @Column(unique = true)
  private String email;
  @Column
  private String name;
  @Column
  private Long pokeToken;
}
