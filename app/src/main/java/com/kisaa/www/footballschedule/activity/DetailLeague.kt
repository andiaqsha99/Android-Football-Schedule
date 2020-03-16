package com.kisaa.www.footballschedule.activity

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.R
import com.kisaa.www.footballschedule.adapter.PagerAdapter
import com.kisaa.www.footballschedule.invisible
import com.kisaa.www.footballschedule.model.Detail
import com.kisaa.www.footballschedule.presenter.DetailPresenter
import com.kisaa.www.footballschedule.view.DetailView
import com.kisaa.www.footballschedule.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_league.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

class DetailLeague : AppCompatActivity(),
    DetailView {

    private var detail: MutableList<Detail> = mutableListOf()
    private lateinit var presenter: DetailPresenter
    private lateinit var idLeague: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Detail League"
            setDisplayHomeAsUpEnabled(true)
        }

        idLeague = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")

        title_league.text = name
        btn_list_team.setOnClickListener{
            startActivity<TeamListActivity>("id" to idLeague)
        }

        val pagerAdapter = PagerAdapter(
            idLeague,
            supportFragmentManager
        )
        viewPager.adapter = pagerAdapter
        tabs.setupWithViewPager(viewPager)

        val request = ApiRepository()
        val gson = Gson()

        presenter = DetailPresenter(
            this,
            request,
            gson
        )
        presenter.getLeagueList(idLeague)

        swipeDetail.onRefresh {
            presenter.getLeagueList(idLeague)
        }
    }

    override fun showLoading() {
        progress_bar.visible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
    }

    override fun showDetailLeague(data: Detail) {
        swipeDetail.isRefreshing = false
        detail.clear()
        detail.add(data)

        Picasso.get().load(detail[0].logo).into(league_logo)
        country_league.text = detail[0].country
        web_league.apply {
            text = detail[0].website
            setOnClickListener {
                browse("https://"+ detail[0].website)
            }
        }
        event_league.text = detail[0].firstEvent
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView =  menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Cari Pertandingan"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) {
                    startActivity<SearchResult>("search" to query, "id" to idLeague)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.favorite_match -> {
                startActivity<FavoriteMatch>()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
