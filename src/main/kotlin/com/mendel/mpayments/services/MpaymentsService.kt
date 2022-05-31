package com.mendel.mpayments.services

import com.mendel.mpayments.builders.TransactionBuilder
import com.mendel.mpayments.model.vo.TransactionRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MpaymentsService {

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    fun createNewTransaction(transactionRequest: TransactionRequest, transactionId: Long) {
        val transaction = TransactionBuilder
                            .withAmount(transactionRequest.amount)
                            .withType(transactionRequest.type)
                            .withParentId(transactionRequest.parentId)
                            .withId(transactionId)
                            .build()
        transactionRepository.saveTransaction(transaction)
    }

    fun getTransactionWithType(type: String): Collection<Long> {
        return transactionRepository.getTransactionWithType(type)
    }

    fun getSumAmountForParentId(parentId:Long): Double {
        val txs = transactionRepository.getTransactionsWithParentId(parentId)

        return txs.sumOf { tx -> tx.amount }
    }
}