package com.kisaa.www.footballschedule.response

import com.kisaa.www.footballschedule.model.Standings

data class StandingResponse(
    val table: List<Standings>
)