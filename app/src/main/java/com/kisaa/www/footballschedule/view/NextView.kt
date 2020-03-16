package com.kisaa.www.footballschedule.view

import com.kisaa.www.footballschedule.model.Match

interface NextView {
    fun showMatch(data: List<Match>)
}