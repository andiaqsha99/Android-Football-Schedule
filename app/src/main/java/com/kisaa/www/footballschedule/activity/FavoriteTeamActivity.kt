package com.kisaa.www.footballschedule.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kisaa.www.footballschedule.R
import com.kisaa.www.footballschedule.adapter.FavoriteTeamAdapter
import com.kisaa.www.footballschedule.database
import com.kisaa.www.footballschedule.model.FavoriteTeam
import kotlinx.android.synthetic.main.activity_favorite_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity

class FavoriteTeamActivity : AppCompatActivity() {

    private var favorite: MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var adapter: FavoriteTeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_team)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Favorite Team"
        }

        adapter = FavoriteTeamAdapter(this, favorite){
            startActivity<TeamDetailActivity>("id" to it.idTeam)
        }
        rv_favorite_team.layoutManager = LinearLayoutManager(this)
        rv_favorite_team.adapter = adapter

        showFavorite()
    }

    private fun showFavorite() {
        favorite.clear()
        database.use {
            val result = select(FavoriteTeam.TABLE_TEAM)
            val list = result.parseList(classParser<FavoriteTeam>())
            favorite.addAll(list)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }
}
