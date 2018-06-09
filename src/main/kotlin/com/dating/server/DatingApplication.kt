package com.dating.server

import com.dating.server.spring.DBConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import javax.sql.DataSource
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Profile

@SpringBootApplication
class DatingApplication {

    @Bean
    @Profile("dev","prod")
    fun dataSource(configuration: DBConfiguration): DataSource {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.url(configuration.dataBaseUrl)
        dataSourceBuilder.username(configuration.dataBaseUser)
        dataSourceBuilder.password(configuration.dataBasePassword)
        return dataSourceBuilder.build()
    }

}


fun main(args: Array<String>) {
    val i=0
    runApplication<DatingApplication>(*args)
}



