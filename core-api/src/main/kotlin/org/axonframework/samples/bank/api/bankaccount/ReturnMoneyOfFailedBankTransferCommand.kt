package org.axonframework.samples.bank.api.bankaccount

import org.axonframework.commandhandling.TargetAggregateIdentifier

class ReturnMoneyOfFailedBankTransferCommand(
    @field:TargetAggregateIdentifier var bankAccountId: String?,
    var amount: Long
)
