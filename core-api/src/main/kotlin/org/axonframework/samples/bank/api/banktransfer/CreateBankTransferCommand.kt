package org.axonframework.samples.bank.api.banktransfer

import org.axonframework.commandhandling.TargetAggregateIdentifier

class CreateBankTransferCommand(
    @field:TargetAggregateIdentifier var bankTransferId: String?,
    var sourceBankAccountId: String?,
    var destinationBankAccountId: String?,
    var amount: Long
)