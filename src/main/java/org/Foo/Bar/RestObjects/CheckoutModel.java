package org.Foo.Bar.RestObjects;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckoutModel {
  private String itemName;
  private Long quantities;
  private Long amount;
}
