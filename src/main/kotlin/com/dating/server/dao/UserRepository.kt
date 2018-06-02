package com.dating.server.dao

import com.dating.server.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, String>
