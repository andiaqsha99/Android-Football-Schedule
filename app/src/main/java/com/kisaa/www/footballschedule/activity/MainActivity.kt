package com.kisaa.www.footballschedule.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kisaa.www.footballschedule.R
import com.kisaa.www.footballschedule.adapter.LeagueAdapter
import com.kisaa.www.footballschedule.model.League
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private var league: MutableList<League> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        league_list.layoutManager = LinearLayoutManager(this)
        league_list.adapter =
            LeagueAdapter(this, league) {
                startActivity<DetailLeague>("id" to it.leagueId, "name" to it.leagueName)
            }
    }

    private fun initData() {
        val name = resources.getStringArray(R.array.league_title)
        val id = resources.getStringArray(R.array.league_id)

        league.clear()
        for (i in name.indices) {
            league.add(
                League(
                    name[i],
                    id[i]
                )
            )
        }

    }
}
