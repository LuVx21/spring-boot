package org.luvx.kotlin.service.impl

import org.luvx.java.service.UserReadService
import org.luvx.kotlin.entity.User
import org.luvx.kotlin.repository.UserRepository
import org.luvx.kotlin.service.UserService
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service
class UserServiceImpl : UserService {
    private val log = mu.KotlinLogging.logger {}

    @Resource
    private lateinit var userRepository: UserRepository

    @Resource
    private lateinit var userReadService: UserReadService

    override fun save(user: User): User {
        log.info { "入库 $user" }
        val id = user.id
        if (id != null) {
            val u = userReadService.selectById(id)
            if (u != null) {
                return u
            }
        }
        return userRepository.save(user)
    }

    override fun delete(user: User) {
        log.info { "删除 $user" }
        val id = user.id
        if (id != null) {
            userRepository.deleteById(id)
            return
        }

        userRepository.delete(user)
    }
}