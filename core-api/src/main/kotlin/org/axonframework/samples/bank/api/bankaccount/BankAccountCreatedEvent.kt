package org.axonframework.samples.bank.api.bankaccount

class BankAccountCreatedEvent(
    var id: String?,
    var overdraftLimit: Long
)
