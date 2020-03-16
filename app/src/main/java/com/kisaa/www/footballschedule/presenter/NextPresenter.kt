package com.kisaa.www.footballschedule.presenter

import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.CoroutineContextProvider
import com.kisaa.www.footballschedule.TheSportDBApi
import com.kisaa.www.footballschedule.fragment.NextFragment
import com.kisaa.www.footballschedule.response.MatchResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NextPresenter(
    private val view: NextFragment,
    private val apiRepository: ApiRepository,
    private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getMatchList(id: String?){
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(
                        TheSportDBApi.getNextMatch(
                            id
                        )
                    ).await(),
                MatchResponse::class.java
            )
            view.showMatch(data.events)
        }
    }
}