package org.axonframework.samples.bank.web.dto;

public class BankTransferDto {

  private String sourceBankAccountId;
  private String destinationBankAccountId;
  private long amount;

  @Override
  public String toString() {
    return "BankTransferDto{" +
        "sourceBankAccountId='" + sourceBankAccountId + '\'' +
        ", destinationBankAccountId='" + destinationBankAccountId + '\'' +
        ", amount=" + amount +
        '}';
  }

  public String getSourceBankAccountId() {
    return sourceBankAccountId;
  }

  public void setSourceBankAccountId(String sourceBankAccountId) {
    this.sourceBankAccountId = sourceBankAccountId;
  }

  public String getDestinationBankAccountId() {
    return destinationBankAccountId;
  }

  public void setDestinationBankAccountId(String destinationBankAccountId) {
    this.destinationBankAccountId = destinationBankAccountId;
  }

  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
  }
}
