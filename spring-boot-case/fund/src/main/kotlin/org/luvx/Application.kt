package org.luvx

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

private val log = mu.KotlinLogging.logger {}

@MapperScan("org.luvx.fund")
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
    log.info { "启动成功......" }
}
