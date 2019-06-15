package org.axonframework.samples.bank.api.bankaccount

class DestinationBankAccountCreditedEvent(
    id: String,
    amount: Long,
    val bankTransferId: String) : MoneyAddedEvent(id, amount)
