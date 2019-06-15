package org.axonframework.samples.bank.web;

import java.util.UUID;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.samples.bank.api.banktransfer.CreateBankTransferCommand;
import org.axonframework.samples.bank.query.banktransfer.BankTransferEntry;
import org.axonframework.samples.bank.query.banktransfer.BankTransferRepository;
import org.axonframework.samples.bank.web.dto.BankTransferDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class BankTransferController {

  private final static Logger log = LoggerFactory.getLogger(BankTransferController.class);
  private final CommandGateway commandGateway;
  private final BankTransferRepository bankTransferRepository;

  public BankTransferController(CommandGateway commandGateway, BankTransferRepository bankTransferRepository) {
    this.commandGateway = commandGateway;
    this.bankTransferRepository = bankTransferRepository;
  }

  @SubscribeMapping("/bank-accounts/{bankAccountId}/bank-transfers")
  public Iterable<BankTransferEntry> bankTransfers(@DestinationVariable String bankAccountId) {
    log.info("Retrieve bank transfers for bank account with id {}", bankAccountId);
    return bankTransferRepository.findBySourceBankAccountIdOrDestinationBankAccountId(bankAccountId, bankAccountId);
  }

  @MessageMapping("/bank-transfers/{id}")
  public BankTransferEntry get(@DestinationVariable String id) {
    log.info("Retrieve bank transfer with id {}", id);
    return bankTransferRepository.findOne(id);
  }

  @MessageMapping("/bank-transfers/create")
  public void create(BankTransferDto bankTransferDto) {
    log.info("Create bank transfer with payload {}", bankTransferDto);

    String bankTransferId = UUID.randomUUID().toString();
    CreateBankTransferCommand command = new CreateBankTransferCommand(bankTransferId,
        bankTransferDto.getSourceBankAccountId(),
        bankTransferDto.getDestinationBankAccountId(),
        bankTransferDto.getAmount());

    commandGateway.send(command);
  }
}
