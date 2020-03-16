package com.kisaa.www.footballschedule.presenter

import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.TestContextProvider
import com.kisaa.www.footballschedule.fragment.PrevFragment
import com.kisaa.www.footballschedule.model.Match
import com.kisaa.www.footballschedule.response.MatchResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PrevPresenterTest {

    @Mock
    private lateinit var view: PrevFragment

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: PrevPresenter

    @Before
    fun setUpMock(){
        MockitoAnnotations.initMocks(this)
        presenter = PrevPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getMatchList() {
        val matchs: MutableList<Match> = mutableListOf()
        val response = MatchResponse(matchs)
        val leagueId = "4346"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", MatchResponse::class.java)).thenReturn(response)

            presenter.getMatchList(leagueId)
            Mockito.verify(view).showMatch(matchs)
        }
    }
}