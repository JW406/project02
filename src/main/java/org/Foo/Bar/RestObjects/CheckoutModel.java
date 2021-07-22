package org.Foo.Bar.RestObjects;

public class CheckoutModel {
  private String itemName;
  private Long quantities;
  private Long amount;

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public Long getQuantities() {
    return quantities;
  }

  public void setQuantities(Long quantities) {
    this.quantities = quantities;
  }

  public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }
}
