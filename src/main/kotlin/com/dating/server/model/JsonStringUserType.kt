package com.dating.server.model

import org.hibernate.HibernateException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor
import org.hibernate.usertype.ParameterizedType
import org.hibernate.usertype.UserType
import java.io.Serializable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

/**
 * Created by daishin@gmail.com
 * 18.05.2016.
 */
class JsonStringUserType : ParameterizedType, UserType {

    override fun sqlTypes(): IntArray {
        return intArrayOf(VarcharTypeDescriptor.INSTANCE.sqlType)
    }

    override fun returnedClass(): Class<*> {
        return ProfileQuestions::class.java
    }

    @Throws(HibernateException::class)
    override fun equals(o: Any?, o1: Any?): Boolean {
        return if (o == null || o1 == null) {
            false
        } else o == o1
    }

    @Throws(HibernateException::class)
    override fun hashCode(o: Any?): Int {
        return o?.hashCode() ?: 0
    }

    @Throws(HibernateException::class, SQLException::class)
    override fun nullSafeGet(rs: ResultSet, names: Array<String>, session: SharedSessionContractImplementor, owner: Any): Any? {
//        val value = StringType.INSTANCE.get(rs, names[0], session) as String
//        if (value == null)
//            return null
//        else {
//            val gson = Gson()
//            return gson.fromJson<Any>(value, returnedClass())
//        }
        return  ""
    }

    @Throws(HibernateException::class, SQLException::class)
    override fun nullSafeSet(st: PreparedStatement, value: Any?, index: Int, session: SharedSessionContractImplementor) {
//        if (value == null)
//            StringType.INSTANCE.set(st, null, index, session)
//        else {
//            val gson = Gson()
//            StringType.INSTANCE.set(st, gson.toJson(value), index, session)
//        }
    }


    @Throws(HibernateException::class)
    override fun deepCopy(o: Any): Any {
        return o
    }

    override fun isMutable(): Boolean {
        return false
    }

    @Throws(HibernateException::class)
    override fun disassemble(value: Any): Serializable {
        return value as Serializable
    }

    @Throws(HibernateException::class)
    override fun assemble(cached: Serializable, owner: Any): Any {
        return cached
    }

    @Throws(HibernateException::class)
    override fun replace(original: Any, target: Any, owner: Any): Any {
        return original
    }

    override fun setParameterValues(properties: Properties) {
        val i = 0

    }
}
