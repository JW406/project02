package org.Foo.Bar.EntitiesDao;

import org.Foo.Bar.Entities.PokemonItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokeItemDao extends JpaRepository<PokemonItem, Integer> {
}
