package org.axonframework.samples.bank.api.bankaccount

abstract class MoneyAddedEvent(
    var bankAccountId: String?,
    var amount: Long
)
