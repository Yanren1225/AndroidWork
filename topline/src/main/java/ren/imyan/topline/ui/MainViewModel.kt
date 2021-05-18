package ren.imyan.topline.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ren.imyan.topline.core.ServiceCreator
import ren.imyan.topline.data.ConvertData
import ren.imyan.topline.data.MutableConvertData
import ren.imyan.topline.data.moshi.News
import ren.imyan.topline.data.retrofit.ToplineService
import ren.imyan.topline.ui.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-27 17:47
 * @website https://imyan.ren
 */
class MainViewModel : ViewModel() {
    val fragmentList: List<Fragment>
        get() = arrayListOf(
            HomeFragment(),
            Fragment(),
            Fragment()
        )

    val newsListData: ConvertData<List<News>>
        get() = _newsListData.toImmutable()

    private val _newsListData = MutableConvertData<List<News>>()

    val adListData: ConvertData<List<News>>
        get() = _adListData.toImmutable()

    private val _adListData = MutableConvertData<List<News>>()

    init {
        getNewsData()
        getADData()
    }

    fun getNewsData() {
        val newDataService = ServiceCreator.create<ToplineService>()
        newDataService.getData("home_news_list_data").enqueue(object : Callback<List<News>> {
            override fun onResponse(call: Call<List<News>>, response: Response<List<News>>) {
                response.body()?.let {
                    _newsListData.data.value = it
                }
            }

            override fun onFailure(call: Call<List<News>>, t: Throwable) {
                _newsListData.state.value = t.toString()
            }

        })
    }

    fun getADData(){
        val newDataService = ServiceCreator.create<ToplineService>()
        newDataService.getData("home_ad_list_data").enqueue(object : Callback<List<News>> {
            override fun onResponse(call: Call<List<News>>, response: Response<List<News>>) {
                response.body()?.let {
                    _adListData.data.value = it
                }
            }

            override fun onFailure(call: Call<List<News>>, t: Throwable) {
                _adListData.state.value = t.toString()
            }

        })
    }

}