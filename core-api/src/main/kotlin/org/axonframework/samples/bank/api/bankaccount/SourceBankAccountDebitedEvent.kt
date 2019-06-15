package org.axonframework.samples.bank.api.bankaccount

class SourceBankAccountDebitedEvent(
    id: String,
    amountOfMoney: Long,
    val bankTransferId: String
) : MoneySubtractedEvent(id, amountOfMoney)
