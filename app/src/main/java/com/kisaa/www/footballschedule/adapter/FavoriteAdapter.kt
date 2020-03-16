package com.kisaa.www.footballschedule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kisaa.www.footballschedule.R
import com.kisaa.www.footballschedule.model.Favorite
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_match.view.*

class FavoriteAdapter (private val context: Context, private val item: List<Favorite>,
                       private val listener: (Favorite) -> Unit):
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_match,
                parent,
                false
            )
        )

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(item[position],listener)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bindItem(items: Favorite, listener: (Favorite) -> Unit){
            itemView.apply {
                match_date.text = items.matchDate
                match_clock.text = items.matchTime
                home_team.text = items.homeName
                away_team.text = items.awayName
                setOnClickListener { listener(items) }
            }
            Picasso.get().load(items.awayBadge).into(itemView.away_logo)
            Picasso.get().load(items.homeBadge).into(itemView.home_logo)

            if (items.homeScore != null){
                itemView.home_score.text = items.homeScore
                itemView.away_score.text = items.awayScore
            }
        }
    }
}