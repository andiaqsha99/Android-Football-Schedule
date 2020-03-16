package com.kisaa.www.footballschedule.adapter

import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kisaa.www.footballschedule.fragment.NextFragment
import com.kisaa.www.footballschedule.fragment.PrevFragment
import com.kisaa.www.footballschedule.fragment.StandingFragment

class PagerAdapter(private val idLeague: String, fm: FragmentManager):
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val title = arrayListOf( "Prev Match", "Next Match", "Standings")
    private val season = "1920"

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment =
                PrevFragment.newInstance(idLeague)
            1 -> fragment =
                NextFragment.newInstance(idLeague)
            2 -> fragment =
                StandingFragment.newInstance(idLeague, season)
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }

    override fun getCount() = title.size
}