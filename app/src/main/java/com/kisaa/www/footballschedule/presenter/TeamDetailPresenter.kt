package com.kisaa.www.footballschedule.presenter

import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.CoroutineContextProvider
import com.kisaa.www.footballschedule.TheSportDBApi
import com.kisaa.www.footballschedule.activity.TeamDetailActivity
import com.kisaa.www.footballschedule.response.TeamResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamDetailPresenter(
    private val view: TeamDetailActivity,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getDetailTeam(idTeam: String?){
        GlobalScope.launch(context.main){
            view.showLoading()
            val data = gson.fromJson(
                apiRepository.doRequestAsync(
                    TheSportDBApi.getTeamDetail(idTeam)
                ).await(),
                TeamResponse::class.java
            )
            view.showDetailTeam(data.teams[0])
            view.hideLoading()
        }
    }
}