package org.axonframework.samples.bank.api.bankaccount

class MoneyWithdrawnEvent(
    bankAccountId: String,
    amountOfMoney: Long
) : MoneySubtractedEvent(bankAccountId, amountOfMoney)