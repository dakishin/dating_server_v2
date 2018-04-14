package com.dating.server;

import io.reactivex.Single;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dakishin@mail.com  14.04.18.
 */
@RestController
@RequestMapping(value = "/")
public class Api {

    @GetMapping(value = "hello")
    public Single<Hello> all() {
        return Single.just(new Hello());
    }


}
