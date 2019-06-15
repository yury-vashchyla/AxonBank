package org.axonframework.samples.bank.command;

import static org.axonframework.eventhandling.GenericEventMessage.asEventMessage;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.AggregateNotFoundException;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.samples.bank.api.bankaccount.CreditDestinationBankAccountCommand;
import org.axonframework.samples.bank.api.bankaccount.DebitSourceBankAccountCommand;
import org.axonframework.samples.bank.api.bankaccount.DestinationBankAccountNotFoundEvent;
import org.axonframework.samples.bank.api.bankaccount.SourceBankAccountNotFoundEvent;

public class BankAccountCommandHandler {

  private Repository<BankAccount> repository;
  private EventBus eventBus;

  public BankAccountCommandHandler(Repository<BankAccount> repository, EventBus eventBus) {
    this.repository = repository;
    this.eventBus = eventBus;
  }

  @CommandHandler
  public void handle(DebitSourceBankAccountCommand command) {
    try {
      Aggregate<BankAccount> bankAccountAggregate = repository.load(command.getBankAccountId());
      bankAccountAggregate.execute(bankAccount -> bankAccount
          .debit(command.getAmount(), command.getBankTransferId()));
    } catch (AggregateNotFoundException exception) {
      eventBus.publish(asEventMessage(new SourceBankAccountNotFoundEvent(command.getBankTransferId())));
    }
  }

  @CommandHandler
  public void handle(CreditDestinationBankAccountCommand command) {
    try {
      Aggregate<BankAccount> bankAccountAggregate = repository.load(command.getBankAccountId());
      bankAccountAggregate.execute(bankAccount -> bankAccount
          .credit(command.getAmount(), command.getBankTransferId()));
    } catch (AggregateNotFoundException exception) {
      eventBus.publish(asEventMessage(new DestinationBankAccountNotFoundEvent(command.getBankTransferId())));
    }
  }
}
