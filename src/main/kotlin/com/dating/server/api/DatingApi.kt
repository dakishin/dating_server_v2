package com.dating.server.api

import com.dating.server.dao.TelegramUserRepository
import com.dating.server.dao.TrebaRepository
import com.dating.server.model.*
import com.dating.server.model.TrebaDTOApi
import com.dating.server.service.PurchaseService
import com.dating.server.service.TelegramUserService
import com.dating.server.service.TrebaService
import io.reactivex.Single
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.util.ArrayList

/**
 * Created by dakishin@mail.com  14.04.18.
 */
@RestController
@RequestMapping(path = ["/api_v3"])
class DatingApi(val purchaseService: PurchaseService, val telegramUserService: TelegramUserService,
                val trebaService: TrebaService, val trebaRepository: TrebaRepository,
                val telegramUserRepository: TelegramUserRepository) {

    @Transient
    private val LOG = LoggerFactory.getLogger(DatingApi::class.java)

//curl -i http://localhost:8080/api_v3/test_get
    @GetMapping(path = ["/test_get"])
    fun testGet(): Single<String> = Single.just("Response")

//curl --header "Content-Type: application/json" --request POST --data '{"param1":"xyz","param2":22}' http://localhost:8080/api_postest_
    data class TestBody(val param1:String,val param2:Int)
    @PostMapping(path = ["/test_post"])
    fun testPost(@RequestBody testParam: TestBody): Single<String> = Single.just("Response")


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


    @PostMapping(path = ["/getTrebas/{telegramId}"])
    fun getTrebas(@PathVariable("telegramId") telegramId: Int) =
            executeApiMethod {
                val trebas = trebaRepository.getByOwner(telegramId.toString())
                val trebaDTOApis = ArrayList<TrebaDTOApi>()
                trebas.forEach {
                    trebaDTOApis.add(TrebaDTOApi.fromTreba(it))
                }
                trebaDTOApis
            }


    @PostMapping(path = ["/sendGeoDataV2"])
    fun sendGeoDatav2(@RequestBody param: SendGeoDataParam) =
            executeApiMethod {
                telegramUserService.saveGeoData(param.telegramId.toString(), param.lat, param.lon, param.city)
                Unit
            }


    @GetMapping("/search/{telegramId}")
    fun search(@PathVariable("telegramId") telegramId: String) =
            executeApiMethod {
                telegramUserRepository.searchNear(telegramId)
            }


    @PostMapping(path = ["/createPurchase"])
    fun createPurchase(@RequestBody param: CreatePurchaseParam) =
            executeApiMethod {
                purchaseService.create(param.telegramId, param.sku, param.orderId)
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






