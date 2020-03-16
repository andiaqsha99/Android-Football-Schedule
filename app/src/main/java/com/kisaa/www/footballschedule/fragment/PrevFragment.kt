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
import com.kisaa.www.footballschedule.activity.DetailMatch
import com.kisaa.www.footballschedule.adapter.MatchAdapter
import com.kisaa.www.footballschedule.model.Match
import com.kisaa.www.footballschedule.presenter.PrevPresenter
import com.kisaa.www.footballschedule.view.PrevView
import kotlinx.android.synthetic.main.fragment_prev.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class PrevFragment : Fragment() , PrevView {

    private val matchs: MutableList<Match> = mutableListOf()
    private lateinit var presenter: PrevPresenter

    companion object{
        private const val LEAGUE_ID = "league_id"

        fun newInstance(id: String): PrevFragment{
            val fragment = PrevFragment()
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
        return inflater.inflate(R.layout.fragment_prev, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val leagueId = arguments?.getString(LEAGUE_ID)
        val request = ApiRepository()
        val gson = Gson()

        presenter = PrevPresenter(
            this,
            request,
            gson
        )
        presenter.getMatchList(leagueId)

    }

    override fun showMatch(data: List<Match>) {

        matchs.clear()
        matchs.addAll(data)

        rv_prev_match.layoutManager = LinearLayoutManager(activity)
        rv_prev_match.adapter = MatchAdapter(matchs){
            startActivity<DetailMatch>("id" to it.idMatch)
        }
    }
}
