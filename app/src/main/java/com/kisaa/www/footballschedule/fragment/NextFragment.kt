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
import com.kisaa.www.footballschedule.adapter.MatchAdapter
import com.kisaa.www.footballschedule.model.Match
import com.kisaa.www.footballschedule.presenter.NextPresenter
import com.kisaa.www.footballschedule.view.NextView
import kotlinx.android.synthetic.main.fragment_next.*

/**
 * A simple [Fragment] subclass.
 */
class NextFragment : Fragment() , NextView {

    private val matchs: MutableList<Match> = mutableListOf()
    private lateinit var presenter: NextPresenter

    companion object{
        private const val LEAGUE_ID = "league_id"

        fun newInstance(id: String): NextFragment{
            val fragment = NextFragment()
            val bundle = Bundle()
            bundle.putString(LEAGUE_ID, id)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val leagueId = arguments?.getString(LEAGUE_ID)
        val request = ApiRepository()
        val gson = Gson()

        presenter = NextPresenter(
            this,
            request,
            gson
        )
        presenter.getMatchList(leagueId)
    }

    override fun showMatch(data: List<Match>) {
        matchs.clear()
        matchs.addAll(data)

        rv_next_match.layoutManager = LinearLayoutManager(activity)
        rv_next_match.adapter = MatchAdapter(matchs){
            //do nothing
        }
    }
}
