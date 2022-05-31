package com.mendel.mpayments.services

import com.mendel.mpayments.model.Transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class TransactionRepository {
    private val logger: Logger = LoggerFactory.getLogger(TransactionRepository::class.java)

    @Value("\${redis.prefix-key}")
    private lateinit var prefixRedisKey: String
    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, Transaction>

    fun saveTransaction(transaction: Transaction) {
        redisTemplate.opsForValue().set(prefixRedisKey+transaction.transactionId, transaction)
        redisTemplate.boundListOps(prefixRedisKey+transaction.type).leftPush(transaction)
    }

    fun getTransactionWithType(type: String): Collection<Long> {
        val sizeList = redisTemplate.boundListOps(prefixRedisKey+type).size() ?: 1
        val txs = redisTemplate.boundListOps(prefixRedisKey+type).range(0, sizeList)

        return (txs?.map { tx -> tx.transactionId } ?: emptyList())  as Collection<Long>
    }

    fun getTransactionWithId(transactionId: String): Transaction {
        val tx = redisTemplate.opsForValue().get(prefixRedisKey+transactionId)
        tx?.let { return tx }
        throw Exception()
    }
}
