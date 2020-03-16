package com.kisaa.www.footballschedule.presenter

import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.CoroutineContextProvider
import com.kisaa.www.footballschedule.TheSportDBApi
import com.kisaa.www.footballschedule.activity.SearchResult
import com.kisaa.www.footballschedule.model.Match
import com.kisaa.www.footballschedule.response.SearchResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchResultPresenter(
    private val view: SearchResult,
    private val apiRepository: ApiRepository,
    private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getSearchResult(query: String?, idLeague: String){
        GlobalScope.launch(context.main) {
            val data : SearchResponse? = gson.fromJson(
                apiRepository
                    .doRequestAsync(
                        TheSportDBApi.getSearchResult(
                            query
                        )
                    ).await(),
                SearchResponse::class.java
            )

            val soccers: List<Match>? = data?.event?.filter { it.typeSport == "Soccer" && it.idLeague == idLeague }
            if(soccers != null){
                view.showResult(soccers)
            } else {
                view.showNoResult()
            }

            view.hideLoading()
        }
    }
}