package org.luvx.kotlin.repository

import org.luvx.kotlin.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
}