package msa.gateway

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.ZuulFilterResult
import com.netflix.zuul.context.RequestContext
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
class GatewayService

fun main(args: Array<String>) {
	runApplication<GatewayService>(*args)
}

@Configuration
class GatewayConfig{

	@Bean
	fun preFilter():ZuulFilter = PreFilter()
}

class PreFilter: ZuulFilter(){

	private val logger = LoggerFactory.getLogger(this.javaClass)

	override fun run(): Any? {
		val ctx = RequestContext.getCurrentContext()
		val request = ctx.request

		logger.info(">> request : $request")
		return null
	}

	override fun shouldFilter(): Boolean {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun compareTo(other: ZuulFilter?): Int {
		return super.compareTo(other)
	}

	override fun isStaticFilter(): Boolean {
		return super.isStaticFilter()
	}

	override fun filterType(): String {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun isFilterDisabled(): Boolean {
		return super.isFilterDisabled()
	}

	override fun disablePropertyName(): String {
		return super.disablePropertyName()
	}

	override fun runFilter(): ZuulFilterResult {
		return super.runFilter()
	}

	override fun filterOrder(): Int {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun equals(other: Any?): Boolean {
		return super.equals(other)
	}

	override fun hashCode(): Int {
		return super.hashCode()
	}

	override fun toString(): String {
		return super.toString()
	}
}