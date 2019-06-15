package org.axonframework.samples.bank.api.banktransfer

class BankTransferCreatedEvent(
    var bankTransferId: String?,
    var sourceBankAccountId: String?,
    var destinationBankAccountId: String?,
    var amount: Long
)
