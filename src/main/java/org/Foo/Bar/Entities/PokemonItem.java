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
@Table(name = "proj02_PokemonItem")
public class PokemonItem {
  @Setter(value = AccessLevel.NONE)
  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJ02_POKEMONITEM")
  @SequenceGenerator(name = "PROJ02_POKEMONITEM", sequenceName = "PROJ02_POKEMONITEM_S", allocationSize = 1, initialValue = 200)
  @Column
  private Integer id;

  @Column(name = "poke_name")
  private String pokeName;

  @Column
  private Long price;

  @Column
  private String img;
}
