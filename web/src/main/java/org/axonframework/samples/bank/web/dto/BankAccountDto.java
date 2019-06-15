package org.axonframework.samples.bank.web.dto;

public class BankAccountDto {

  private long overdraftLimit;

  public long getOverdraftLimit() {
    return overdraftLimit;
  }

  public void setOverdraftLimit(long overdraftLimit) {
    this.overdraftLimit = overdraftLimit;
  }
}
