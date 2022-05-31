package com.mendel.mpayments.services

import com.mendel.mpayments.model.Transaction
import com.mendel.mpayments.utils.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class TransactionRepository {

    @Autowired
    private lateinit var utils: Utils
    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, Transaction>

    companion object{
        private val ZERO_LIMIT = 0L
        private val ONE_LIMIT = 1L
    }

    fun saveTransaction(transaction: Transaction) {
        redisTemplate.opsForValue().set(utils.getRedisTransactionIdKey(transaction.transactionId), transaction)
        redisTemplate.boundListOps(utils.getRedisTypeKey(transaction.type)).leftPush(transaction)
        redisTemplate.boundListOps(utils.getRedisPidKey(transaction.parentId)).leftPush(transaction)
    }

    fun getTransactionWithType(type: String): Collection<Long> {
        val sizeList = redisTemplate.boundListOps(utils.getRedisTypeKey(type)).size() ?: ONE_LIMIT
        val txs = redisTemplate.boundListOps(utils.getRedisTypeKey(type)).range(ZERO_LIMIT, sizeList)

        return (txs?.map { tx -> tx.transactionId } ?: emptyList())
    }

    fun getTransactionsWithParentId(parentId: Long): Collection<Transaction> {
        val sizeList = redisTemplate.boundListOps(utils.getRedisPidKey(parentId)).size() ?: ONE_LIMIT
        return redisTemplate.boundListOps(utils.getRedisPidKey(parentId)).range(ZERO_LIMIT, sizeList) ?: emptyList()
    }

    fun getTransactionWithId(pidTransactionId: Long): Transaction {
        val tx = redisTemplate.opsForValue().get(utils.getRedisPidKey(pidTransactionId))
        tx?.let { return tx }
        throw Exception()
    }
}
