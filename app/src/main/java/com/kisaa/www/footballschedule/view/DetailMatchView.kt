package com.kisaa.www.footballschedule.view

import com.kisaa.www.footballschedule.model.Match
import com.kisaa.www.footballschedule.model.Team

interface DetailMatchView {
    fun setTeamLogo(home: List<Team>, away: List<Team>)
    fun showMatchDetail(data: Match)
}