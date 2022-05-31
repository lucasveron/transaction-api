package com.mendel.mpayments.model.vo

data class TransactionRequest(
    val amount: Double,
    val type: String,
    val parentId: Long
)
