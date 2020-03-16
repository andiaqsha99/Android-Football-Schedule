package com.kisaa.www.footballschedule.presenter

import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.CoroutineContextProvider
import com.kisaa.www.footballschedule.TheSportDBApi
import com.kisaa.www.footballschedule.fragment.PrevFragment
import com.kisaa.www.footballschedule.response.MatchResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PrevPresenter(
    private val view: PrevFragment,
    private val apiRepository: ApiRepository,
    private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getMatchList(id: String?){
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(
                        TheSportDBApi.getPrevMatch(
                            id
                        )
                    ).await(),
                MatchResponse::class.java
            )
            view.showMatch(data.events)
        }
    }
}