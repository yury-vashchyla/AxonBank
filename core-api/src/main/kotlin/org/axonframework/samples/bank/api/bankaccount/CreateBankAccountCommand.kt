package org.axonframework.samples.bank.api.bankaccount

import org.axonframework.commandhandling.TargetAggregateIdentifier
import javax.validation.constraints.Min

class CreateBankAccountCommand(
    @field:TargetAggregateIdentifier
    var bankAccountId: String?,
    @field:Min(value = 0, message = "Overdraft limit must not be less than zero")
    var overdraftLimit: Long
)
