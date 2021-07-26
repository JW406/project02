package org.Foo.Bar.Services;

import org.Foo.Bar.Entities.PokeTransaction;
import org.Foo.Bar.Entities.TokenTxLog;

public interface TransactionService {
  TokenTxLog triggerTransaction(PokeTransaction tx, String email);
}
