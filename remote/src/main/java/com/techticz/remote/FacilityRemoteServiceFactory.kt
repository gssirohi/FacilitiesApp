package com.techticz.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object FacilityRemoteServiceFactory {

    fun makeFacilityRemoteService(isDebug: Boolean): FacilityRemoteService {
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor(isDebug))
        return makeFacilityService(okHttpClient, makeGson())
    }

    private fun makeFacilityService(okHttpClient: OkHttpClient, gson: Gson): FacilityRemoteService {
        val retrofit = Retrofit.Builder()
            .baseUrl(RemoteConstants.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(FacilityRemoteService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    private fun makeGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    private val RESPONSE_STRING: String = ""

    private fun makeLoggingInterceptor(isDebug: Boolean): Interceptor {
        /*var interceptor = object: Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                return Response.Builder()
                    .code(200)
                    .message(RESPONSE_STRING)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), RESPONSE_STRING.toByteArray()))
                    .addHeader("content-type", "application/json")
                    .build()
            }

        }
        return interceptor;*/
        //---------------------------------
         val logging = HttpLoggingInterceptor()
         logging.level = if (isDebug)
             HttpLoggingInterceptor.Level.BODY
         else
           HttpLoggingInterceptor.Level.NONE
         return logging
    }
}