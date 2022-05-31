package com.mendel.mpayments.builders

import com.mendel.mpayments.model.Transaction

class TransactionBuilder {
    companion object {
        private var transactionId: Long = 0
        private var amount: Double = 0.0
        private var type: String = ""
        private var parentId: Long = 0

        fun withAmount(amount: Double): Companion {
            this.amount = amount
            return this
        }
        fun withType(type: String): Companion {
            this.type = type
            return this
        }
        fun withParentId(parentId: Long): Companion {
            this.parentId = parentId
            return this
        }
        fun withId(transactionId: Long): Companion {
            this.transactionId = transactionId
            return this
        }
        fun build() = Transaction(transactionId, amount, type, parentId)

    }
}
