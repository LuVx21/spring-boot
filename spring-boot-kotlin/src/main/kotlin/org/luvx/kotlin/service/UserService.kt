package org.luvx.kotlin.service

import org.luvx.kotlin.entity.User

interface UserService {

    fun test() {
        println("ok")
    }

    fun getUser(id: Long): User
}