package com.kisaa.www.footballschedule.view

import com.kisaa.www.footballschedule.model.Team

interface TeamListView {
    fun showLoading()
    fun hideLoading()
    fun showTeamsList(data: List<Team>)
}