package com.kisaa.www.footballschedule.presenter

import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.TestContextProvider
import com.kisaa.www.footballschedule.activity.DetailLeague
import com.kisaa.www.footballschedule.model.Detail
import com.kisaa.www.footballschedule.response.DetailResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailPresenterTest {

    @Mock
    private lateinit var view: DetailLeague

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: DetailPresenter

    @Before
    fun setUpMock(){
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getLeagueList() {
        val detail: MutableList<Detail> = mutableListOf(Detail())
        val response = DetailResponse(detail)
        val id = "4346"

        runBlocking{
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", DetailResponse::class.java)).thenReturn(response)

            presenter.getLeagueList(id)

            Mockito.verify(view).hideLoading()
            Mockito.verify(view).showDetailLeague(detail[0])
        }

    }
}