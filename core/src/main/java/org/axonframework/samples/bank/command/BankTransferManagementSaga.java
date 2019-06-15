package org.axonframework.samples.bank.command;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.samples.bank.api.bankaccount.CreditDestinationBankAccountCommand;
import org.axonframework.samples.bank.api.bankaccount.DebitSourceBankAccountCommand;
import org.axonframework.samples.bank.api.bankaccount.DestinationBankAccountCreditedEvent;
import org.axonframework.samples.bank.api.bankaccount.DestinationBankAccountNotFoundEvent;
import org.axonframework.samples.bank.api.bankaccount.ReturnMoneyOfFailedBankTransferCommand;
import org.axonframework.samples.bank.api.bankaccount.SourceBankAccountDebitRejectedEvent;
import org.axonframework.samples.bank.api.bankaccount.SourceBankAccountDebitedEvent;
import org.axonframework.samples.bank.api.bankaccount.SourceBankAccountNotFoundEvent;
import org.axonframework.samples.bank.api.banktransfer.BankTransferCreatedEvent;
import org.axonframework.samples.bank.api.banktransfer.MarkBankTransferCompletedCommand;
import org.axonframework.samples.bank.api.banktransfer.MarkBankTransferFailedCommand;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class BankTransferManagementSaga {

  private transient CommandBus commandBus;
  private String sourceBankAccountId;
  private String destinationBankAccountId;
  private long amount;

  @Autowired
  public void setCommandBus(CommandBus commandBus) {
    this.commandBus = commandBus;
  }

  @StartSaga
  @SagaEventHandler(associationProperty = "bankTransferId")
  public void on(BankTransferCreatedEvent event) {
    this.sourceBankAccountId = event.getSourceBankAccountId();
    this.destinationBankAccountId = event.getDestinationBankAccountId();
    this.amount = event.getAmount();

    DebitSourceBankAccountCommand command = new DebitSourceBankAccountCommand(event.getSourceBankAccountId(),
        event.getBankTransferId(),
        event.getAmount());
    commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
  }

  @SagaEventHandler(associationProperty = "bankTransferId")
  @EndSaga
  public void on(SourceBankAccountNotFoundEvent event) {
    MarkBankTransferFailedCommand markFailedCommand = new MarkBankTransferFailedCommand(event.getBankTransferId());
    commandBus.dispatch(asCommandMessage(markFailedCommand), LoggingCallback.INSTANCE);
  }

  @SagaEventHandler(associationProperty = "bankTransferId")
  @EndSaga
  public void on(SourceBankAccountDebitRejectedEvent event) {
    MarkBankTransferFailedCommand markFailedCommand = new MarkBankTransferFailedCommand(event.getBankTransferId());
    commandBus.dispatch(asCommandMessage(markFailedCommand), LoggingCallback.INSTANCE);
  }

  @SagaEventHandler(associationProperty = "bankTransferId")
  public void on(SourceBankAccountDebitedEvent event) {
    CreditDestinationBankAccountCommand command = new CreditDestinationBankAccountCommand(destinationBankAccountId,
        event.getBankTransferId(),
        event.getAmount());
    commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
  }

  @SagaEventHandler(associationProperty = "bankTransferId")
  @EndSaga
  public void on(DestinationBankAccountNotFoundEvent event) {
    ReturnMoneyOfFailedBankTransferCommand returnMoneyCommand = new ReturnMoneyOfFailedBankTransferCommand(
        sourceBankAccountId,
        amount);
    commandBus.dispatch(asCommandMessage(returnMoneyCommand), LoggingCallback.INSTANCE);

    MarkBankTransferFailedCommand markFailedCommand = new MarkBankTransferFailedCommand(
        event.getBankTransferId());
    commandBus.dispatch(asCommandMessage(markFailedCommand), LoggingCallback.INSTANCE);
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "bankTransferId")
  public void on(DestinationBankAccountCreditedEvent event) {
    MarkBankTransferCompletedCommand command = new MarkBankTransferCompletedCommand(event.getBankTransferId());
    commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
  }
}
