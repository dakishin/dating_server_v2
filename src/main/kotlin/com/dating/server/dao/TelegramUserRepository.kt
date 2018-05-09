/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dating.server.dao

import com.dating.server.model.TelegramUser
import com.dating.server.model.TelegramUserDistance
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface TelegramUserRepository : CrudRepository<TelegramUser, String> {


    @Query("select user from TelegramUser user  where user.telegramId= :telegramId ")
    fun getByTelegramId(@Param("telegramId") telegramId: String): TelegramUser?

    @Query("SELECT " +
            "" +
            " calc_distance(user_near.latitude,user_near.longitude,user_owner.latitude,user_owner.longitude) AS distance, " +
            " user_near.* " +
            "FROM " +
            " telegram_user AS user_near ,telegram_user AS user_owner" +
            " WHERE" +
            " (user_near.latitude IS NOT NULL) AND (user_near.longitude IS NOT NULL) AND user_owner.telegram_id=:telegramId AND (user_near.telegram_id<>user_owner.telegram_id)" +
            " ORDER BY " +
            " distance ASC", nativeQuery = true)
    fun searchNear(@Param("telegramId") telegramId: String): List<TelegramUserDistance>
}