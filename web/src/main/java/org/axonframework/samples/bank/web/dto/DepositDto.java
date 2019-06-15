package org.axonframework.samples.bank.web.dto;

public class DepositDto {

  private String bankAccountId;
  private long amount;

  public String getBankAccountId() {
    return bankAccountId;
  }

  public void setBankAccountId(String bankAccountId) {
    this.bankAccountId = bankAccountId;
  }

  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
  }
}
