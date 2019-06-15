package org.axonframework.samples.bank.api.bankaccount

import org.axonframework.commandhandling.TargetAggregateIdentifier

class WithdrawMoneyCommand(
    @field:TargetAggregateIdentifier var bankAccountId: String?,
    var amountOfMoney: Long
)
