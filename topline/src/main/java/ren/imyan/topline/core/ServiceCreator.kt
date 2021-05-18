package ren.imyan.topline.core

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @author EndureBlaze/炎忍 https://github.com.EndureBlaze
 * @data 2021-01-24 17:57
 * @website https://imyan.ren
 */
object ServiceCreator {
    private const val BASE_URL = "http://cn.utools.club:38608/topline/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val original: Request = it.request()

            val request = original.newBuilder()
                .header("Content-Type", "application/json; charset=utf-8")
                .method(original.method, original.body)
                .build()

            it.proceed(request)
        }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}