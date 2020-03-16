package com.kisaa.www.footballschedule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kisaa.www.footballschedule.R
import com.kisaa.www.footballschedule.model.FavoriteTeam
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_team.view.*

class FavoriteTeamAdapter(private val context: Context, private val items: List<FavoriteTeam>,
                          private val listener: (FavoriteTeam) -> Unit):
    RecyclerView.Adapter<FavoriteTeamAdapter.ViewHolder>(){

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bindItem(team: FavoriteTeam, listener: (FavoriteTeam) -> Unit){
            itemView.apply {
                tv_team_name.text = team.teamName
                tv_team_desc.text = team.teamDesc
                setOnClickListener {
                    listener(team)
                }
            }
            Picasso.get().load(team.teamBadge).into(itemView.img_team_badge)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_team,
                parent,
                false
            )
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }
}