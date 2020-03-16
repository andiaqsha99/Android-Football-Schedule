package com.kisaa.www.footballschedule.activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.R
import com.kisaa.www.footballschedule.database
import com.kisaa.www.footballschedule.model.Favorite
import com.kisaa.www.footballschedule.model.Match
import com.kisaa.www.footballschedule.model.Team
import com.kisaa.www.footballschedule.presenter.DetailMatchPresenter
import com.kisaa.www.footballschedule.view.DetailMatchView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class DetailMatch : AppCompatActivity(), DetailMatchView {

    private lateinit var presenter: DetailMatchPresenter
    private var matchs: Match = Match()
    private lateinit var id: String
    private lateinit var homeLogo: String
    private lateinit var awayLogo: String
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        supportActionBar?.title = "Detail Match"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        id = intent.getStringExtra("id")
        val request = ApiRepository()
        val gson = Gson()

        presenter = DetailMatchPresenter(this,request,gson )
        presenter.getDetailEvent(id)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
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

    override fun setTeamLogo(home: List<Team>, away: List<Team>) {
        homeLogo = home[0].teamBadge!!
        awayLogo = away[0].teamBadge!!

        Picasso.get().load(homeLogo).into(home_logo)
        Picasso.get().load(awayLogo).into(away_logo)
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(
                    Favorite.TABLE_FAVORITE,
                    Favorite.MATCH_ID to matchs.idMatch,
                    Favorite.HOME_ID to matchs.idHome,
                    Favorite.HOME_NAME to matchs.homeTeam,
                    Favorite.HOME_BADGE to homeLogo,
                    Favorite.HOME_SCORE to matchs.homeScore,
                    Favorite.AWAY_ID to matchs.idAway,
                    Favorite.AWAY_NAME to matchs.awayTeam,
                    Favorite.AWAY_BADGE to awayLogo,
                    Favorite.AWAY_SCORE to matchs.awayScore,
                    Favorite.MATCH_CLOCK to matchs.matchTime,
                    Favorite.MATCH_DATE to matchs.matchDate)
                Toast.makeText(this@DetailMatch,"Add to Favorite",
                    Toast.LENGTH_SHORT).show()
            }
        }catch (e: SQLiteConstraintException){
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(
                    Favorite.TABLE_FAVORITE, "(MATCH_ID = {id})",
                    "id" to matchs.idMatch!!)
                Toast.makeText(this@DetailMatch,"Remove from Favorite",
                    Toast.LENGTH_SHORT).show()
            }
        }catch (e: SQLiteConstraintException){
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
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
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs("(MATCH_ID = {id})", "id" to matchs.idMatch!!)
            val favorite = result.parseList(classParser<Favorite>())
            if(favorite.isNotEmpty()) isFavorite = true
        }
    }

    override fun showMatchDetail(data: Match) {
        matchs = data
        presenter.getTeamLogo(matchs.idHome, matchs.idAway)

        match_clock.text = matchs.matchTime
        match_date.text = matchs.matchDate

        home_team.text = matchs.homeTeam
        home_score.text = matchs.homeScore
        h_score.text = matchs.homeScore
        h_red_cards.text = matchs.homeRed
        h_yellow_cards.text = matchs.homeYellow
        h_goal_detail.text = matchs.homeGoalDetail
        h_goalkeeper.text = matchs.homeGoalkeeper
        h_defense.text = matchs.homeDefense
        h_midfield.text = matchs.homeMidfield
        h_forward.text = matchs.homeFroward

        away_team.text = matchs.awayTeam
        away_score.text = matchs.awayScore
        a_score.text = matchs.awayScore
        a_red_cards.text = matchs.awayRed
        a_yellow_cards.text = matchs.awayYellow
        a_goal_detail.text = matchs.awayGoalDetail
        a_goalkeeper.text = matchs.awayGoalkeeper
        a_defense.text = matchs.awayDefense
        a_midfield.text = matchs.awayMidfield
        a_forward.text = matchs.awayFroward

        favoriteState()
        setFavoriteIcon()
    }
}
