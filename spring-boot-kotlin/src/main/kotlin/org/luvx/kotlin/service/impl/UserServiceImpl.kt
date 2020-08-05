package org.luvx.kotlin.service.impl

import org.luvx.kotlin.entity.User
import org.luvx.kotlin.repository.UserRepository
import org.luvx.kotlin.service.UserService
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service
class UserServiceImpl : UserService {
    @Resource
    private lateinit var userRepository: UserRepository

    override fun getUser(id: Long): User {
        val one = userRepository.findById(id)
        return one.get()
    }
}