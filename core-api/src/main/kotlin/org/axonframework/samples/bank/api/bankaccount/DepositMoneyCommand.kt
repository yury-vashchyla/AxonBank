package org.axonframework.samples.bank.api.bankaccount

import org.axonframework.commandhandling.TargetAggregateIdentifier

class DepositMoneyCommand(
    @field:TargetAggregateIdentifier var bankAccountId: String?,
    var amountOfMoney: Long
)