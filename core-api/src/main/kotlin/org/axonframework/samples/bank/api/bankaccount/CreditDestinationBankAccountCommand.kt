package org.axonframework.samples.bank.api.bankaccount

import org.axonframework.commandhandling.TargetAggregateIdentifier

class CreditDestinationBankAccountCommand(
    @field:TargetAggregateIdentifier var bankAccountId: String?,
    var bankTransferId: String?,
    var amount: Long
)
