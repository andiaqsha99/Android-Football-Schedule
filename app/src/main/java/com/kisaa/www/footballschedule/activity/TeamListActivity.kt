package com.kisaa.www.footballschedule.activity

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.R
import com.kisaa.www.footballschedule.adapter.TeamAdapter
import com.kisaa.www.footballschedule.invisible
import com.kisaa.www.footballschedule.model.Team
import com.kisaa.www.footballschedule.presenter.TeamListPresenter
import com.kisaa.www.footballschedule.view.TeamListView
import com.kisaa.www.footballschedule.visible
import kotlinx.android.synthetic.main.activity_team_list.*
import org.jetbrains.anko.startActivity

class TeamListActivity : AppCompatActivity(), TeamListView {

    private val teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamListPresenter
    private lateinit var adapter: TeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_list)
        tv_team_notFound.invisible()

        val idLeague: String? = intent.getStringExtra("id")
        val request = ApiRepository()
        val gson = Gson()

        presenter = TeamListPresenter(this, request, gson)
        presenter.getListTeam(idLeague)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Team List"
        }
    }

    override fun showTeamsList(data: List<Team>) {
        teams.clear()
        teams.addAll(data)

        rv_team.layoutManager = LinearLayoutManager(this)
        adapter = TeamAdapter{
            startActivity<TeamDetailActivity>("id" to it.teamId)
        }
        adapter.setData(teams)
        rv_team.adapter = adapter
    }

    override fun hideLoading() {
        team_progress_bar.invisible()
    }

    override fun showLoading() {
        team_progress_bar.visible()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_team, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search_team)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Team name ..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query: CharSequence = newText as CharSequence
                val teams: List<Team> = teams.filter {
                    it.teamName!!.contains(query, ignoreCase = true)
                }
                if (teams.isNotEmpty()){
                    tv_team_notFound.invisible()
                    rv_team.visible()
                    adapter.updateData(teams)
                } else {
                    rv_team.invisible()
                    tv_team_notFound.visible()
                }
                return true
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.favorite_team -> {
                startActivity<FavoriteTeamActivity>()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
