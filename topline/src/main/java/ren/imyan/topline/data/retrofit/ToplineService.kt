package ren.imyan.topline.data.retrofit

import ren.imyan.topline.data.moshi.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-05-06 15:24
 * @website https://imyan.ren
 */
interface ToplineService {
    @GET("{data}.json")
    fun getData(@Path("data") fileName: String): Call<List<News>>
}