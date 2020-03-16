package com.kisaa.www.footballschedule.presenter

import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.CoroutineContextProvider
import com.kisaa.www.footballschedule.TheSportDBApi
import com.kisaa.www.footballschedule.activity.TeamListActivity
import com.kisaa.www.footballschedule.response.TeamResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamListPresenter(
    private val view: TeamListActivity,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getListTeam(idLeague: String?){
        GlobalScope.launch(context.main){
            view.showLoading()
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(
                        TheSportDBApi.getTeamList(idLeague)
                    ).await(),
                TeamResponse::class.java
            )
            view.showTeamsList(data.teams)
            view.hideLoading()
        }
    }
}