package com.kisaa.www.footballschedule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.R
import com.kisaa.www.footballschedule.TheSportDBApi
import com.kisaa.www.footballschedule.model.League
import com.kisaa.www.footballschedule.response.DetailResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueAdapter(private val context: Context, private val item: List<League>,
                    private val listener: (League) -> Unit):
    RecyclerView.Adapter<LeagueAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list,
                parent,
                false
            )
        )

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(item[position], listener)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bindItem(items: League, listener: (League) -> Unit){
            itemView.txt_league.text = items.leagueName
            itemView.setOnClickListener{listener(items)}
            setLeagueBadge(items.leagueId)
        }

        private fun setLeagueBadge(id: String?){
            val request = ApiRepository()
            val gson = Gson()
            GlobalScope.launch(Dispatchers.Main) {
                val data = gson.fromJson(
                    request.doRequestAsync(
                        TheSportDBApi.getLeagueDetail(id)).await(),
                    DetailResponse::class.java
                )
                 Picasso.get().load(data.leagues[0].logo).into(itemView.iv_league)
            }
        }
    }


}