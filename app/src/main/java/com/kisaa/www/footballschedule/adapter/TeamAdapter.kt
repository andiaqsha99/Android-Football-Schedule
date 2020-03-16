package com.kisaa.www.footballschedule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kisaa.www.footballschedule.R
import com.kisaa.www.footballschedule.model.Team
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_team.view.*

class TeamAdapter(private val listener: (Team) -> Unit):
    RecyclerView.Adapter<TeamAdapter.ViewHolder>(){

    private var team: MutableList<Team> = mutableListOf()

    fun setData(data: List<Team>){
        team.clear()
        team.addAll(data)
        notifyDataSetChanged()
    }

    fun updateData(data: List<Team>){
        team.clear()
        team.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bindItem(teams: Team, listener: (Team) -> Unit){
            itemView.apply {
                tv_team_name.text = teams.teamName
                tv_team_desc.text = teams.teamDescription
                setOnClickListener {
                    listener(teams)
                }
            }
            Picasso.get().load(teams.teamBadge).into(itemView.img_team_badge)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_team,
                parent,
                false
            )
        )

    override fun getItemCount() = team.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(team[position], listener)
    }
}