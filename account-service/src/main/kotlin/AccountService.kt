package msa.account

import net.bytebuddy.utility.RandomString
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.groups.Default

@EnableEurekaClient
@SpringBootApplication
class AccountService

fun main(args: Array<String>) {
	runApplication<AccountService>(*args)
}

@RefreshScope
@RestController
class TestController(
	@Value("\${say.cheese}") private val say: String
){

	@GetMapping("/say")
	fun say(): String{
		return say
	}
}

@RestController
class AccountController(
		private val accountRepository: AccountRepository
){

	@PostMapping
	fun create(
			@Validated(ValidationGroups.Create::class) @RequestBody account: Account,
			bindingResult: BindingResult): ResponseEntity<Account>{

		if(bindingResult.hasErrors()){
			throw BindException(bindingResult)
		}

		return ResponseEntity.ok(accountRepository.save(account))
	}

	@GetMapping
	fun list(): List<Account> = accountRepository.findAll()

	@GetMapping("/{id}")
	fun details(id: String): Account{
		return accountRepository.findById(id).get()
	}

	@PatchMapping("/{id}/article-count")
	fun patchArticleCount(
			@PathVariable id: String,
			@RequestBody account: Account, bindingResult: BindingResult
	): ResponseEntity<String>{

		accountRepository.findById(id).ifPresent{
			val a = it.copy(articleCount = account.articleCount)
			accountRepository.save(a)
		}

		return ResponseEntity.ok(account.articleCount.toString())
	}

	@DeleteMapping("/{id}")
	fun dalete(@PathVariable id: String){
		accountRepository.deleteById(id)
	}

}

interface AccountRepository: JpaRepository<Account, String>

@Entity
@Table(name = "ACCOUNTS")
data class Account(
		@Id
		val id: String = RandomString.make(10),

		val username: String? = null,

		val password: String? = null,

		val email: String? = null,

		val name: String? = null,

		val articleCount: Int = 0,

		val createAt: Date? = Date()
)

object ValidationGroups{
	interface Create : Default
}