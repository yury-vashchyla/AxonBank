package org.axonframework.samples.bank.command;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.samples.bank.api.bankaccount.BankAccountCreatedEvent;
import org.axonframework.samples.bank.api.bankaccount.CreateBankAccountCommand;
import org.axonframework.samples.bank.api.bankaccount.DepositMoneyCommand;
import org.axonframework.samples.bank.api.bankaccount.DestinationBankAccountCreditedEvent;
import org.axonframework.samples.bank.api.bankaccount.MoneyAddedEvent;
import org.axonframework.samples.bank.api.bankaccount.MoneyDepositedEvent;
import org.axonframework.samples.bank.api.bankaccount.MoneyOfFailedBankTransferReturnedEvent;
import org.axonframework.samples.bank.api.bankaccount.MoneySubtractedEvent;
import org.axonframework.samples.bank.api.bankaccount.MoneyWithdrawnEvent;
import org.axonframework.samples.bank.api.bankaccount.ReturnMoneyOfFailedBankTransferCommand;
import org.axonframework.samples.bank.api.bankaccount.SourceBankAccountDebitRejectedEvent;
import org.axonframework.samples.bank.api.bankaccount.SourceBankAccountDebitedEvent;
import org.axonframework.samples.bank.api.bankaccount.WithdrawMoneyCommand;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class BankAccount {

  @AggregateIdentifier
  private String id;
  private long overdraftLimit;
  private long balanceInCents;

  @SuppressWarnings("unused")
  private BankAccount() {
  }

  @CommandHandler
  public BankAccount(CreateBankAccountCommand command) {
    apply(new BankAccountCreatedEvent(command.getBankAccountId(), command.getOverdraftLimit()));
  }

  @CommandHandler
  public void deposit(DepositMoneyCommand command) {
    apply(new MoneyDepositedEvent(id, command.getAmountOfMoney()));
  }

  @CommandHandler
  public void withdraw(WithdrawMoneyCommand command) {
    if (command.getAmountOfMoney() <= balanceInCents + overdraftLimit) {
      apply(new MoneyWithdrawnEvent(id, command.getAmountOfMoney()));
    }
  }

  public void debit(long amount, String bankTransferId) {
    if (amount <= balanceInCents + overdraftLimit) {
      apply(new SourceBankAccountDebitedEvent(id, amount, bankTransferId));
    } else {
      apply(new SourceBankAccountDebitRejectedEvent(bankTransferId));
    }
  }

  public void credit(long amount, String bankTransferId) {
    apply(new DestinationBankAccountCreditedEvent(id, amount, bankTransferId));
  }

  @CommandHandler
  public void returnMoney(ReturnMoneyOfFailedBankTransferCommand command) {
    apply(new MoneyOfFailedBankTransferReturnedEvent(id, command.getAmount()));
  }

  @EventSourcingHandler
  public void on(BankAccountCreatedEvent event) {
    this.id = event.getId();
    this.overdraftLimit = event.getOverdraftLimit();
    this.balanceInCents = 0;
  }

  @EventSourcingHandler
  public void on(MoneyAddedEvent event) {
    balanceInCents += event.getAmount();
  }

  @EventSourcingHandler
  public void on(MoneySubtractedEvent event) {
    balanceInCents -= event.getAmount();
  }
}
