package com.mendel.mpayments.builders

import com.mendel.mpayments.model.Transaction

class TransactionBuilder {
    companion object {
        private var transactionId: Long = 0
        private var amount: Double = 0.0
        private var type: String = ""
        private var parentId: Long = 0

        fun withAmount(amount: Double): Companion {
            if( amount < 0) throw Exception("Invalid Transaction 'amount': amount is negative")
            this.amount = amount
            return this
        }
        fun withType(type: String): Companion {
            if( type.isBlank()) throw Exception("Invalid Transaction 'type': type is blank")
            this.type = type
            return this
        }
        fun withParentId(parentId: Long): Companion {
            if( parentId < 0 ) throw Exception("Invalid Transaction 'parentId': parentId is negative")
            this.parentId = parentId
            return this
        }
        fun withId(transactionId: Long): Companion {
            if(transactionId < 0) throw Exception("Invalid Transaction 'transactionId': transactionId is negative")
            this.transactionId = transactionId
            return this
        }
        fun build() = Transaction(transactionId, amount, type, parentId)
    }
}
