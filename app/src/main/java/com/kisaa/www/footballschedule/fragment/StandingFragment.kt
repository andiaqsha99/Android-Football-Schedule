package com.kisaa.www.footballschedule.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.kisaa.www.footballschedule.ApiRepository
import com.kisaa.www.footballschedule.R
import com.kisaa.www.footballschedule.adapter.StandingAdapter
import com.kisaa.www.footballschedule.model.Standings
import com.kisaa.www.footballschedule.presenter.StandingPresenter
import com.kisaa.www.footballschedule.view.StandingView
import kotlinx.android.synthetic.main.fragment_standing.*

/**
 * A simple [Fragment] subclass.
 */
class StandingFragment : Fragment(), StandingView {

    private val standings: MutableList<Standings> = mutableListOf()
    private lateinit var presenter: StandingPresenter

    companion object{
        private const val LEAGUE_ID = "league_id"
        private const val LEAGUE_SEASON = "league_season"

        fun newInstance(id: String, season: String): StandingFragment{
            val fragment = StandingFragment()
            val bundle = Bundle()
            bundle.putString(LEAGUE_ID, id)
            bundle.putString(LEAGUE_SEASON, season)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_standing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val leagueId = arguments?.getString(LEAGUE_ID)
        val season = arguments?.getString(LEAGUE_SEASON)
        val request = ApiRepository()
        val gson = Gson()

        presenter = StandingPresenter(this, request, gson)
        presenter.getStandingList(leagueId, season)
    }

    override fun showStanding(data: List<Standings>) {
        standings.clear()
        standings.addAll(data)

        rv_standings.layoutManager = LinearLayoutManager(activity)
        rv_standings.adapter = StandingAdapter(standings)
    }
}
