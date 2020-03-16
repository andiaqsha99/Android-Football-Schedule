package com.kisaa.www.footballschedule.view

import com.kisaa.www.footballschedule.model.Standings

interface StandingView {
    fun showStanding(data: List<Standings>)
}