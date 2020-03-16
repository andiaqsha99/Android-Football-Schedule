package com.kisaa.www.footballschedule.presenter

import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.TestContextProvider
import com.kisaa.www.footballschedule.activity.DetailMatch
import com.kisaa.www.footballschedule.model.Match
import com.kisaa.www.footballschedule.model.Team
import com.kisaa.www.footballschedule.response.MatchResponse
import com.kisaa.www.footballschedule.response.TeamResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailMatchPresenterTest {

    @Mock
    private lateinit var view: DetailMatch

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: DetailMatchPresenter

    @Before
    fun setUpMock(){
        MockitoAnnotations.initMocks(this)
        presenter = DetailMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeamLogo() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val homeId = "4416"
        val awayId = "4415"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", TeamResponse::class.java)).thenReturn(response)

            presenter.getTeamLogo(homeId, awayId)
            Mockito.verify(view).setTeamLogo(teams,teams)
        }
    }

    @Test
    fun getDetailEvent() {
        val matchs: MutableList<Match> = mutableListOf(Match())
        val response = MatchResponse(matchs)
        val eventId = "441613"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", MatchResponse::class.java)).thenReturn(response)

            presenter.getDetailEvent(eventId)
            Mockito.verify(view).showMatchDetail(matchs[0])
        }
    }
}