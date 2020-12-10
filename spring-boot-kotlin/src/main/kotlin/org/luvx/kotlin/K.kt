package org.luvx.kotlin

import org.springframework.stereotype.Component

@Component
class K {
    private val log = mu.KotlinLogging.logger {}

    fun method(s: String): String {
        log.info { "参数: $s" }
        return "hello $s from kotlin!"
    }
}