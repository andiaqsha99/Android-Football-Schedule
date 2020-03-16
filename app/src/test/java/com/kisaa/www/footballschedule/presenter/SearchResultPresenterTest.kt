package com.kisaa.www.footballschedule.presenter

import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.TestContextProvider
import com.kisaa.www.footballschedule.activity.SearchResult
import com.kisaa.www.footballschedule.model.Match
import com.kisaa.www.footballschedule.response.SearchResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchResultPresenterTest {

    @Mock
    private lateinit var view: SearchResult

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: SearchResultPresenter

    @Before
    fun setUpMock(){
        MockitoAnnotations.initMocks(this)
        presenter = SearchResultPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getSearchResult() {
        val matchs: MutableList<Match> = mutableListOf()
        val response = SearchResponse(matchs)
        val query = "Chelsea vs Arsenal"
        val id = "4328"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", SearchResponse::class.java)).thenReturn(response)

            presenter.getSearchResult(query, id)
            

            Mockito.verify(view).showResult(matchs)
            Mockito.verify(view).hideLoading()
        }
    }
}