package org.axonframework.samples.bank.command;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.samples.bank.api.banktransfer.BankTransferCompletedEvent;
import org.axonframework.samples.bank.api.banktransfer.BankTransferCreatedEvent;
import org.axonframework.samples.bank.api.banktransfer.BankTransferFailedEvent;
import org.axonframework.samples.bank.api.banktransfer.CreateBankTransferCommand;
import org.axonframework.samples.bank.api.banktransfer.MarkBankTransferCompletedCommand;
import org.axonframework.samples.bank.api.banktransfer.MarkBankTransferFailedCommand;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class BankTransfer {

  @AggregateIdentifier
  private String BankTransferId;
  private String sourceBankAccountId;
  private String destinationBankAccountId;
  private long amount;
  private Status status;

  @SuppressWarnings("unused")
  protected BankTransfer() {
  }

  @CommandHandler
  public BankTransfer(CreateBankTransferCommand command) {
    apply(new BankTransferCreatedEvent(command.getBankTransferId(),
        command.getSourceBankAccountId(),
        command.getDestinationBankAccountId(),
        command.getAmount()));
  }

  @CommandHandler
  public void handle(MarkBankTransferCompletedCommand command) {
    apply(new BankTransferCompletedEvent(command.getBankTransferId()));
  }

  @CommandHandler
  public void handle(MarkBankTransferFailedCommand command) {
    apply(new BankTransferFailedEvent(command.getBankTransferId()));
  }

  @EventHandler
  public void on(BankTransferCreatedEvent event) throws Exception {
    this.BankTransferId = event.getBankTransferId();
    this.sourceBankAccountId = event.getSourceBankAccountId();
    this.destinationBankAccountId = event.getDestinationBankAccountId();
    this.amount = event.getAmount();
    this.status = Status.STARTED;
  }

  @EventHandler
  public void on(BankTransferCompletedEvent event) {
    this.status = Status.COMPLETED;
  }

  @EventHandler
  public void on(BankTransferFailedEvent event) {
    this.status = Status.FAILED;
  }

  private enum Status {
    STARTED,
    FAILED,
    COMPLETED
  }
}