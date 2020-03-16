package com.kisaa.www.footballschedule.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kisaa.www.footballschedule.R
import com.kisaa.www.footballschedule.adapter.FavoriteAdapter
import com.kisaa.www.footballschedule.database
import com.kisaa.www.footballschedule.model.Favorite
import kotlinx.android.synthetic.main.activity_favorite_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity

class FavoriteMatch : AppCompatActivity() {

    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_match)
        supportActionBar?.title = "Favorite Match"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = FavoriteAdapter(this, favorites){
            startActivity<DetailMatch>("id" to it.matchId)
        }
        rv_favorite_match.layoutManager = LinearLayoutManager(this)
        rv_favorite_match.adapter = adapter
        showFavorite()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showFavorite(){
        favorites.clear()
        database.use {

            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }
}
