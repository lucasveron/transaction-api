package com.mendel.mpayments.utils

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class Utils {
        @Value("\${redis.prefix-key-basic}")
        private lateinit var prefixRedisKey: String
        @Value("\${redis.prefix-key-pid}")
        private lateinit var prefixRedisKeyPid: String

        fun getRedisTransactionIdKey(transactionId: Long) = prefixRedisKey+transactionId
        fun getRedisTypeKey(type: String) = prefixRedisKey+type
        fun getRedisPidKey(parentId: Long) = prefixRedisKey+prefixRedisKeyPid+parentId
}