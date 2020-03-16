package com.kisaa.www.footballschedule.presenter

import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.CoroutineContextProvider
import com.kisaa.www.footballschedule.TheSportDBApi
import com.kisaa.www.footballschedule.activity.DetailMatch
import com.kisaa.www.footballschedule.response.MatchResponse
import com.kisaa.www.footballschedule.response.TeamResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailMatchPresenter(
    private val view: DetailMatch,
    private val apiRepository: ApiRepository,
    private val gson: Gson,  private val context: CoroutineContextProvider = CoroutineContextProvider()
){
    fun getTeamLogo(idHome: String?, idAway: String?){
        GlobalScope.launch(context.main) {
            val home = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.getTeamDetail(idHome)).await(),
                TeamResponse::class.java
            )
            val away = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.getTeamDetail(idAway)).await(),
                TeamResponse::class.java
            )
            view.setTeamLogo(home.teams, away.teams)
        }
    }
    fun getDetailEvent(id: String?){
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.getEventDetail(id)).await(),
                MatchResponse::class.java
            )
            view.showMatchDetail(data.events[0])
        }
    }
}

