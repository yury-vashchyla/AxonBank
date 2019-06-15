package org.axonframework.samples.bank.api.banktransfer

import org.axonframework.commandhandling.TargetAggregateIdentifier

class MarkBankTransferCompletedCommand(
    @field:TargetAggregateIdentifier var bankTransferId: String?
)
