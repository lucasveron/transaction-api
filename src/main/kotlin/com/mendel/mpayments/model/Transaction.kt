package com.mendel.mpayments.model

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class Transaction(
    var transactionId: Long,
    var amount: Double,
    var type: String,
    var parentId: Long
){
    constructor(): this(0,0.0,"",0)
}
