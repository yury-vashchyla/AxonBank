package org.axonframework.samples.bank.api.bankaccount

class MoneyOfFailedBankTransferReturnedEvent(
    bankAccountId: String,
    amountOfMoneyDeposited: Long
) : MoneyAddedEvent(bankAccountId, amountOfMoneyDeposited)
