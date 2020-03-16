package com.kisaa.www.footballschedule.presenter

import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.CoroutineContextProvider
import com.kisaa.www.footballschedule.TheSportDBApi
import com.kisaa.www.footballschedule.fragment.StandingFragment
import com.kisaa.www.footballschedule.response.StandingResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StandingPresenter(
    private val view: StandingFragment,
    private val apiRepository: ApiRepository,
    private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getStandingList(id: String?, season: String?){
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(
                        TheSportDBApi.getStandings(
                            id,
                            season
                        )
                    ).await(),
                StandingResponse::class.java
            )
            view.showStanding(data.table)
        }
    }
}