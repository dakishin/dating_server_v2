package com.dating.server.spring

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("dev")
class DBConfigurationDev : DBConfiguration {

    @Value(value = "\${dating.dev.datasource.url}")
    override
    lateinit var dataBaseUrl: String

    @Value(value = "\${dating.datasource.username}")
    override
    lateinit var dataBaseUser: String

    @Value(value = "\${dating.datasource.password}")
    override
    lateinit var dataBasePassword: String

}

@Component
@Profile("prod")
class DBConfigurationProd : DBConfiguration {

    @Value(value = "\${dating.prod.datasource.url}")
    override
    lateinit var dataBaseUrl: String

    @Value(value = "\${dating.datasource.username}")
    override
    lateinit var dataBaseUser: String

    @Value(value = "\${dating.datasource.password}")
    override
    lateinit var dataBasePassword: String

}

interface DBConfiguration {
    var dataBaseUser: String
    var dataBasePassword: String
    var dataBaseUrl: String
}