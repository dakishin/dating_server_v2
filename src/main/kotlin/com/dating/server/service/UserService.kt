package com.dating.server.service

import com.dating.server.dao.UserRepository
import com.dating.server.model.User
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * Created by dakishin@mail.com  20.04.18.
 */

@Component("userService")
@Transactional
class UserService(private val userRepository: UserRepository) {

    fun createUser(user: User) {
        userRepository.save(user)
    }
}