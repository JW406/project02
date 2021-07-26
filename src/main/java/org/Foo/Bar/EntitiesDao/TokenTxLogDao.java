package org.Foo.Bar.EntitiesDao;

import org.Foo.Bar.Entities.TokenTxLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenTxLogDao extends JpaRepository<TokenTxLog, Integer> {
}
