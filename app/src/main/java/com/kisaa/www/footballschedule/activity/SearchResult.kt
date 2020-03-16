package com.kisaa.www.footballschedule.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.kisaa.www.footballschedule.*
import com.kisaa.www.footballschedule.adapter.MatchAdapter
import com.kisaa.www.footballschedule.model.Match
import com.kisaa.www.footballschedule.presenter.SearchResultPresenter
import com.kisaa.www.footballschedule.view.SearchResultView
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchResult : AppCompatActivity(), SearchResultView {

    private val matchs: MutableList<Match> = mutableListOf()
    private lateinit var presenter: SearchResultPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        txt_tidak_ditemukan.invisible()

        val query = intent.getStringExtra("search")
        val idLeague = intent.getStringExtra("id")
        val request = ApiRepository()
        val gson = Gson()

        presenter = SearchResultPresenter(this, request, gson)
        EspressoIdlingResources.increment()
        presenter.getSearchResult(query, idLeague)
    }

    override fun showResult(data: List<Match>) {

        matchs.clear()
        matchs.addAll(data)

        rv_search.layoutManager = LinearLayoutManager(this)
        rv_search.adapter = MatchAdapter(matchs){}
        if (!EspressoIdlingResources.idlingResource.isIdleNow){
            EspressoIdlingResources.decrement()
        }
    }

    override fun showLoading() {
        progress_bar_search.visible()
    }

    override fun hideLoading() {
        progress_bar_search.invisible()
    }

    override fun showNoResult() {
        if (!EspressoIdlingResources.idlingResource.isIdleNow){
            EspressoIdlingResources.decrement()
        }
        txt_tidak_ditemukan.visible()
        rv_search.invisible()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
