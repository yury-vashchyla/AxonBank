package org.axonframework.samples.bank.api.bankaccount

class MoneyDepositedEvent(
    id: String,
    amountOfMoneyDeposited: Long
) : MoneyAddedEvent(id, amountOfMoneyDeposited)
