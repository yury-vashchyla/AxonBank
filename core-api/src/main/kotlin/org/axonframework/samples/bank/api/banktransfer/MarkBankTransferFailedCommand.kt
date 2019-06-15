package org.axonframework.samples.bank.api.banktransfer

import org.axonframework.commandhandling.TargetAggregateIdentifier

class MarkBankTransferFailedCommand(
    @field:TargetAggregateIdentifier var bankTransferId: String?
)
