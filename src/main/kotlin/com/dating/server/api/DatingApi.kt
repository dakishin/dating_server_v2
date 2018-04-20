package com.dating.server.api

import com.dating.server.model.TelegramUser
import com.dating.server.model.Treba
import com.dating.server.model.TrebaDTOApi
import com.dating.server.service.TelegramUserService
import com.dating.server.service.TrebaService
import io.reactivex.Observable
import io.reactivex.Single
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

/**
 * Created by dakishin@mail.com  14.04.18.
 */
@RestController()
@RequestMapping(path = ["/api_v3"])
class DatingApi(val telegramUserService: TelegramUserService, val trebaService: TrebaService) {

    @Transient
    private val LOG = LoggerFactory.getLogger(DatingApi::class.java)


    @GetMapping(path = ["hello"])
    fun get(): Observable<String> {
        return Observable.just("hello")
    }

    @PostMapping(path = ["/registerTelegramUser"])
    fun createUser(@RequestBody createUserParam: RegisterTelegramUserParam): Single<Response<TelegramUser>> =
            executeApiMethod {
                telegramUserService.create(createUserParam.telegramId, createUserParam.firstName, createUserParam.lastName)
            }

    @PostMapping(path = ["/createTreba"])
    fun createTreba(@RequestBody param: CreateTrebaParam): Single<Response<TrebaDTOApi>> =
            executeApiMethod {
                val treba = trebaService.create(param.telegramUserId, param.priestUuid, param.names, param.type)
                TrebaDTOApi.fromTreba(treba)
            }


    fun <T> executeApiMethod(service: () -> T): Single<Response<T>> =
            Single
                    .fromCallable {
                        service()
                    }
                    .map {
                        Response(result = it)
                    }
                    .doOnError {
                        LOG.error(it.message, it)
                    }
                    .onErrorReturnItem(Response(ErrorCode.UN_EXPECTED))


}

data class RegisterTelegramUserParam(val telegramId: String, val firstName: String? = null, val lastName: String? = null)
data class CreateTrebaParam(val telegramUserId: Int, val names: List<String>, val type: Treba.TrebaType, val priestUuid: String)




