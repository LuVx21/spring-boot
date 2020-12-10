package org.luvx.kotlin

import org.luvx.java.J

private val log = mu.KotlinLogging.logger {}

fun main() {
    val j = J();
    val s = j.method("java")
    log.info { "in kotlin: $s" }
}