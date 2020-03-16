package com.kisaa.www.footballschedule.response

import com.kisaa.www.footballschedule.model.Match

data class SearchResponse(
    val event: List<Match>
)