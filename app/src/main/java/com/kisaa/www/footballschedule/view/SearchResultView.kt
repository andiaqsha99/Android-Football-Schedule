package com.kisaa.www.footballschedule.view

import com.kisaa.www.footballschedule.model.Match

interface SearchResultView {
    fun showLoading()
    fun hideLoading()
    fun showNoResult()
    fun showResult(data: List<Match>)
}