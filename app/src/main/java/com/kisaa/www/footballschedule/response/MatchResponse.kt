package com.kisaa.www.footballschedule.response

import com.kisaa.www.footballschedule.model.Match

data class MatchResponse(
    val events: List<Match>
)