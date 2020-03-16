package com.kisaa.www.footballschedule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.R
import com.kisaa.www.footballschedule.TheSportDBApi
import com.kisaa.www.footballschedule.model.Match
import com.kisaa.www.footballschedule.response.TeamResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_match.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MatchAdapter(private val item: List<Match>,
                   private val listener: (Match) -> Unit):
    RecyclerView.Adapter<MatchAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_match,
                parent,
                false
            )
        )

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(item[position], listener)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bindItem(items: Match, listener: (Match) -> Unit){
            val dateArray = items.matchDate?.split("/")?.toTypedArray()
            val timeArray = items.matchTime?.split(":")?.toTypedArray()

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, Integer.parseInt(dateArray!![2])+2000)
            calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1])-1)
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[0]))
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray!![0])+7)
            calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
            calendar.set(Calendar.SECOND, 0)

            val calendarArray = calendar.time.toString().split(" ").toTypedArray()

            itemView.apply {
                match_date.text = resources.getString(R.string.date_format).format(
                    calendarArray[0], calendarArray[2], calendarArray[1], calendarArray[5]
                )
                match_clock.text = calendarArray[3]
                home_team.text = items.homeTeam
                away_team.text = items.awayTeam
            }

            if (items.homeScore != null){
                itemView.home_score.text = items.homeScore
                itemView.away_score.text = items.awayScore
            }

            setLogo(items.idHome, items.idAway)
            itemView.setOnClickListener { listener(items) }
        }

        private fun setLogo(idHome: String?, idAway: String?){
            val apiRepository = ApiRepository()
            val gson = Gson()

            GlobalScope.launch(Dispatchers.Main) {
                val home = gson.fromJson(
                    apiRepository
                        .doRequestAsync(TheSportDBApi.getTeamDetail(idHome)).await(),
                    TeamResponse::class.java
                )
                val away = gson.fromJson(
                    apiRepository
                        .doRequestAsync(TheSportDBApi.getTeamDetail(idAway)).await(),
                    TeamResponse::class.java
                )

                Picasso.get().load(home.teams[0].teamBadge).into(itemView.home_logo)
                Picasso.get().load(away.teams[0].teamBadge).into(itemView.away_logo)
            }
        }
    }
}