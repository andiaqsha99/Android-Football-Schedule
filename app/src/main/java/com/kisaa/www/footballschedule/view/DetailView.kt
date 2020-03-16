package com.kisaa.www.footballschedule.view

import com.kisaa.www.footballschedule.model.Detail

interface DetailView {
    fun showLoading()
    fun hideLoading()
    fun showDetailLeague(data: Detail)
}