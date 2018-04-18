package com.dating.server.api

import com.dating.server.service.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by dakishin@mail.com  14.04.18.
 */
@RestController
@RequestMapping(value = ["/api"])
class DatingApi(val userService: UserService) {



    @RequestMapping(method = [RequestMethod.POST], name = "create")
    fun createUser(createUserParam: CreateUserParam): CreateUserParam {
        return createUserParam
    }


}

data class CreateUserParam(val name: String)


