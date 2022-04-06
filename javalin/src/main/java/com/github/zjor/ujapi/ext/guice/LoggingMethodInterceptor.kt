package com.github.zjor.ujapi.ext.guice

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.slf4j.LoggerFactory

class LoggingMethodInterceptor : MethodInterceptor {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun invoke(invocation: MethodInvocation): Any {

        val args = invocation.method.parameters
                .zip(invocation.arguments)
                .joinToString(", ") { (param, arg) ->
                    "${param.name}=${arg}"
                }

        try {
            val result = invocation.proceed()
            logger.info("${invocation.method.name}($args): $result")
            return result
        } catch (t: Throwable) {
            logger.info("${invocation.method.name}($args): thrown $t")
            throw t
        }
    }
}