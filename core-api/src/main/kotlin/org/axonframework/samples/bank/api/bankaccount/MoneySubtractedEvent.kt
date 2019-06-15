package org.axonframework.samples.bank.api.bankaccount

abstract class MoneySubtractedEvent(
    var bankAccountId: String?,
    var amount: Long
)
