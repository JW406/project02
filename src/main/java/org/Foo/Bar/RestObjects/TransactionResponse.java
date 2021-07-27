package org.Foo.Bar.RestObjects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponse extends SuccessResponse {
  private Integer txId;
}
