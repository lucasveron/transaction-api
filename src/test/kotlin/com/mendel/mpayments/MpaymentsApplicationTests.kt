package com.mendel.mpayments

import com.mendel.mpayments.model.Transaction
import com.mendel.mpayments.model.vo.TransactionRequest
import com.mendel.mpayments.services.MpaymentsService
import com.mendel.mpayments.services.TransactionRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class MpaymentsApplicationTests {

	@Test
	fun contextLoads() {}

	@Autowired
	lateinit var mpaymentsService: MpaymentsService

	@MockBean
	lateinit var transactionRepository: TransactionRepository

	@Test
	fun `Sum transactions with equal pid sum correctly`() {
		val tx1 = Transaction(1,100.99,"foo",1)
		val tx2 = Transaction(2,99.01,"foo",1)
		Mockito.`when`(transactionRepository.getTransactionsWithParentId(1))
			.thenReturn(listOf(tx1,tx2))
		Assertions.assertEquals( 200.0, mpaymentsService.getSumAmountForParentId(1))
	}

	@Test
	fun `Sum transactions with equal pid sum correctly with different Tx pid`() {
		val tx1 = Transaction(1,100.99,"foo",1)
		val tx2 = Transaction(2,99.01,"foo",1)
		val tx3 = Transaction(3,50.19,"foo",2)

		Mockito.`when`(transactionRepository.getTransactionsWithParentId(1))
			.thenReturn(listOf(tx1,tx2))
		Mockito.`when`(transactionRepository.getTransactionsWithParentId(2))
			.thenReturn(listOf(tx3))

		Assertions.assertEquals( 200.0, mpaymentsService.getSumAmountForParentId(1))
	}

	@Test
	fun `Retrieve transactions with type it's OK`(){
		Mockito.`when`(transactionRepository.getTransactionWithType("market"))
			.thenReturn(listOf(1,2))
		Assertions.assertEquals( 2, mpaymentsService.getTransactionWithType("market").size)
		Assertions.assertTrue( mpaymentsService.getTransactionWithType("market").contains(1))
		Assertions.assertTrue( mpaymentsService.getTransactionWithType("market").contains(2))
	}

	@Test
	fun `When create new transaction with negative amount throw Exception`() {
		Assertions.assertThrows(Exception::class.java) {
			mpaymentsService.createNewTransaction(TransactionRequest(-1.0, "", 1), 1)
		}
	}

	@Test
	fun `When create new transaction with blank type throw Exception`() {
		Assertions.assertThrows(Exception::class.java) {
			mpaymentsService.createNewTransaction(TransactionRequest(1.0, "", 1), 1)
		}
	}

	@Test
	fun `When create new transaction with pid negative throw Exception`() {
		Assertions.assertThrows(Exception::class.java) {
			mpaymentsService.createNewTransaction(TransactionRequest(1.0, "foo", -1), 1)
		}
	}

	@Test
	fun `When create new transaction with transaction id negative throw Exception`() {
		Assertions.assertThrows(Exception::class.java) {
			mpaymentsService.createNewTransaction(TransactionRequest(1.0, "foo", 1), -1)
		}
	}

	@Test
	fun `Create new transaction with positive amount, valid type & valid pid is success`() {
		val newTx = mpaymentsService.createNewTransaction(TransactionRequest(1.0, "foo", 1), 1)
		Assertions.assertNotNull(newTx)
	}
}
