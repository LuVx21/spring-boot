package org.luvx.kotlin.controller

import org.luvx.kotlin.entity.User
import org.luvx.kotlin.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

@RestController
@RequestMapping("/user")
class UserController {
    @Resource
    lateinit var userService: UserService

    @GetMapping("/{userId}")
    fun get(@PathVariable userId: Long): User {
        return userService.getUser(1)
    }
}