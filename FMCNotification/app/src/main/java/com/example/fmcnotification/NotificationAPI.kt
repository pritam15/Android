package com.example.fmcnotification

import com.example.fmcnotification.Constant.Companion.CONTENT_TYPE
import com.example.fmcnotification.Constant.Companion.Server_key
import okhttp3.ResponseBody


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationAPI {

    @Headers("Authroization: Key = $Server_key" , "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body
        notification : PushNotification

    ) : Response<ResponseBody>
}

















