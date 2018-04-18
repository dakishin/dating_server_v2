package com.dating.server.api.template

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by dakishin@mail.com  14.04.18.
 */
//@ExtendWith(SpringExtension::class)
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestRealClient {
//
//    @LocalServerPort
//    var port: Int? = null
//
//
//    // TODO Migrate to WebTestClient when https://youtrack.jetbrains.com/issue/KT-5464 will be fixed
//    private var client: WebClient? = null
//
//
//    @Test
//    fun testHello() {
//        client = WebClient.create("http://localhost:$port")
//        client!!.get().uri("/hello")
//                .accept(MediaType.APPLICATION_JSON_UTF8)
//                .retrieve()
//                .bodyToMono(Hello::class.java)
//                .test()
//                .consumeNextWith {
//                    it == Hello()
//                }
//
//    }


}