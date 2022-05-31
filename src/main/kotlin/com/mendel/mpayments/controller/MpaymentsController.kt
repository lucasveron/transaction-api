package com.mendel.mpayments.controller

import com.mendel.mpayments.model.vo.TransactionRequest
import com.mendel.mpayments.model.vo.TransactionResponse
import com.mendel.mpayments.model.vo.TransactionTypeResponse
import com.mendel.mpayments.services.MpaymentsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController()
class MpaymentsController {

    @Autowired
    private lateinit var mpaymentsService: MpaymentsService

    @PutMapping("/transactions/{transactionId}")
    fun addNewTransaction(@PathVariable transactionId: Long, @RequestBody transactionRequest: TransactionRequest): TransactionResponse {
        mpaymentsService.createNewTransaction(transactionRequest, transactionId)
        return TransactionResponse()
    }

    @GetMapping("/transactions/types/{typeId}")
    fun getTransactionWithType(@PathVariable typeId: String): Collection<Long> {
        return mpaymentsService.getTransactionWithType(typeId)
    }
}