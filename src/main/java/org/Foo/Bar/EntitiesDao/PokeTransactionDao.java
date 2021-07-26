package org.Foo.Bar.EntitiesDao;

import org.Foo.Bar.Entities.PokeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokeTransactionDao extends JpaRepository<PokeTransaction, Integer> {
}
