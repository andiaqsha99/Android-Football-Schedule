package com.kisaa.www.footballschedule.view

import com.kisaa.www.footballschedule.model.Team

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showDetailTeam(data: Team)
}