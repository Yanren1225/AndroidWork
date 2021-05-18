package ren.imyan.topline.data.moshi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-29 14:37
 * @website https://imyan.ren
 */
@JsonClass(generateAdapter = true)
data class News(
    @Json(name = "id")
    val id: Int,
    @Json(name = "type")
    val type: Int,
    @Json(name = "newsName")
    val newsName: String,
    @Json(name = "newsTypeName")
    val newsTypeName: String?,
    @Json(name = "img1")
    val image1: String?,
    @Json(name = "img2")
    val image2: String?,
    @Json(name = "img3")
    val image3: String?,
    @Json(name = "newsUrl")
    val newsUrl: String
)
