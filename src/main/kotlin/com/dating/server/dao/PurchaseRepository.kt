package com.dating.server.dao

import com.dating.server.model.Purchase
import org.springframework.data.repository.CrudRepository

interface PurchaseRepository : CrudRepository<Purchase, String> {

}