package com.kisaa.www.footballschedule.activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.kisaa.www.footballschedule.*
import com.kisaa.www.footballschedule.model.FavoriteTeam
import com.kisaa.www.footballschedule.model.Team
import com.kisaa.www.footballschedule.presenter.TeamDetailPresenter
import com.kisaa.www.footballschedule.view.TeamDetailView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class TeamDetailActivity : AppCompatActivity(), TeamDetailView {

    private var team: Team = Team()
    private lateinit var presenter: TeamDetailPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        val idTeam = intent.getStringExtra("id")
        val request = ApiRepository()
        val gson = Gson()

        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getDetailTeam(idTeam)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(
                    FavoriteTeam.TABLE_TEAM,
                    FavoriteTeam.TEAM_ID to team.teamId,
                    FavoriteTeam.TEAM_NAME to team.teamName,
                    FavoriteTeam.TEAM_BADGE to team.teamBadge,
                    FavoriteTeam.TEAM_DESCRIPTION to team.teamDescription
                )
                Toast.makeText(this@TeamDetailActivity, "Added to favorite",
                    Toast.LENGTH_SHORT).show()
            }
        } catch (e: SQLiteConstraintException){
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(
                    FavoriteTeam.TABLE_TEAM, "(TEAM_ID = {id})",
                    "id" to team.teamId!!
                )
                Toast.makeText(this@TeamDetailActivity, "Remove from favorite",
                    Toast.LENGTH_SHORT).show()
            }
        } catch (e: SQLiteConstraintException){
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showLoading() {
        team_progress_bar.visible()
    }

    override fun hideLoading() {
        team_progress_bar.invisible()
    }

    override fun showDetailTeam(data: Team) {
        team = data

        Picasso.get().load(team.teamBanner).into(iv_banner)
        Picasso.get().load(team.teamBadge).into(civ_badge)
        tv_name.text = team.teamName
        tv_description.text = team.teamDescription
        tv_formed.text = resources.getString(R.string.team_formed, team.teamFormed)
        tv_web.apply {
            text = team.teamWebsite
            setOnClickListener {
                browse("https://"+team.teamWebsite)
            }
        }

        btn_facebook.setOnClickListener {
            browse("https://${team.teamFacebook}")
        }

        btn_instagram.setOnClickListener {
            browse("https://${team.teamInstagram}")
        }

        btn_twitter.setOnClickListener {
            browse("https://${team.teamTwitter}")
        }

        supportActionBar?.apply {
            title = team.teamName
            setDisplayHomeAsUpEnabled(true)
        }

        favoriteState()
        setFavoriteIcon()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_match, menu)
        menuItem = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavoriteIcon()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavoriteIcon(){
        if(isFavorite){
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this,R.drawable.ic_favorite_white_24dp)
        } else {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp)
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteTeam.TABLE_TEAM)
                .whereArgs("(TEAM_ID = {id})", "id" to team.teamId!!)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if(favorite.isNotEmpty()) isFavorite = true
        }
    }
}
