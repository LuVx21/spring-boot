package org.luvx.kotlin.entity.vo

import org.luvx.kotlin.entity.User

fun returnStr2(str: String?): String {
    return str ?: ""
}

fun toLowerStr(str: String) {
    str!!.toLowerCase()
}

fun main() {

    val result0 = "testLet".let {
        println(it.length)
        1000
    }
    println(result0)

    val result00 = "test".also {
        println(it.length)
        1000
    }
    println(result00)

    val user = User()
    user.also { it.id = 1 }
            .also { it.userName = "foo" }
            .also { it.password = "bar" }
            .also { it.age = 18 }

    user.apply { id = 1 }
            .apply { userName = "foo" }
            .apply { password = "bar" }
            .apply { age = 18 }

    println(user)

    val result1 = user.run {
        println("my name is $this.name, I am $age years old, my password is $password")
        1000
    }
    println("result: $result1")

    val result2 = user.apply {
        println("my name is $this.name, I am $age years old, my password is $password")
        1000
    }
    println("result: $result2")

    val result = with(user) {
        println("my name is $this.name, I am $age years old, my password is $password")
        1000
    }
    println("result: $result")

}
