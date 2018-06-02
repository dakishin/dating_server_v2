package com.dating.server.api.samples

import com.dating.server.service.UserService
import org.springframework.beans.factory.annotation.Autowired


//@RunWith(SpringRunner::class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestMockClient {

    @Autowired
    lateinit var userService: UserService

//    @Test
    fun testHello() {
//        val client = WebTestClient.bindToController(DatingApi(userService)).build()
//
//        val createUserParam = Hello()
//        client.post()
//                .uri("/create")
//                .syncBody(createUserParam)
//                .accept(MediaType.APPLICATION_JSON_UTF8)
//                .exchange()
//                .expectBody(Hello::class.java)
//                .equals(createUserParam)


    }
}

data class Hello(val name: String = "name")