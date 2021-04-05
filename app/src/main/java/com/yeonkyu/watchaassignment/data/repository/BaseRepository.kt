package com.yeonkyu.watchaassignment.data.repository

import org.json.JSONObject
import retrofit2.Response

abstract class BaseRepository {
    suspend fun <Any> apiRequest(call: suspend () -> Response<Any>): Any {
        val response = call.invoke()

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.toString()
            val message = StringBuilder()

            message.append(JSONObject(error!!).getString("message"))
            message.append("ERROR Code : ${response.code()}")
            throw Exception(message.toString())
        }
    }
}