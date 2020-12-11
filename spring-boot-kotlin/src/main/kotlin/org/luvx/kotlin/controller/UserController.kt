package org.luvx.kotlin.controller

import org.luvx.java.service.UserReadService
import org.luvx.kotlin.entity.User
import org.luvx.kotlin.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

@RestController
@RequestMapping("/user")
class UserController {
    @Resource
    lateinit var userService: UserService

    @Resource
    lateinit var userReadService: UserReadService

    @GetMapping("/test")
    fun get() {
        val user = User()
        user.id = 50
        user.userName = "foo50"
        user.password = "bar50"
        user.age = 8
        userService.save(user)

        userReadService.select(user)
    }
}