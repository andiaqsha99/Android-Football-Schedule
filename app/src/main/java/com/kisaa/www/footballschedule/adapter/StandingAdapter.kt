package com.kisaa.www.footballschedule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kisaa.www.footballschedule.R
import com.kisaa.www.footballschedule.model.Standings
import kotlinx.android.synthetic.main.item_standing.view.*

class StandingAdapter(private val items: List<Standings>):
    RecyclerView.Adapter<StandingAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bindItem(standing: Standings, position: Int){
            itemView.apply {
                team_name.text = resources.getString(R.string.standing_team).
                    format(position.toString(), standing.name)
                team_play.text = standing.played
                team_goal_for.text = standing.goalsFor
                team_goal_againts.text = standing.goalAgainst
                team_goal_difference.text = standing.goalDiff
                team_win.text = standing.win
                team_draw.text = standing.draw
                team_loss.text = standing.loss
                team_total.text = standing.total
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_standing,
                parent,
                false
            )
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], position+1 )
    }
}