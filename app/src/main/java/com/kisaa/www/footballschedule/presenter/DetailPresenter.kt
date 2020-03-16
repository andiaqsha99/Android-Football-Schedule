package com.kisaa.www.footballschedule.presenter

import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.CoroutineContextProvider
import com.kisaa.www.footballschedule.TheSportDBApi
import com.kisaa.www.footballschedule.activity.DetailLeague
import com.kisaa.www.footballschedule.response.DetailResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailPresenter(
    private val view: DetailLeague,
    private val apiRepository: ApiRepository,
    private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getLeagueList(id: String?){
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(
                        TheSportDBApi.getLeagueDetail(
                            id
                        )
                    ).await(),
                DetailResponse::class.java
            )
            view.hideLoading()
            view.showDetailLeague(data.leagues[0])
        }
    }
}