package org.luvx.kotlin.service

import org.luvx.kotlin.entity.User

interface UserService {
    fun save(user: User): User
    fun delete(user: User)
}