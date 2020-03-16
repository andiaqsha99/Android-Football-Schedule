package com.kisaa.www.footballschedule.view

import com.kisaa.www.footballschedule.model.Match

interface PrevView {
    fun showMatch(data: List<Match>)
}
