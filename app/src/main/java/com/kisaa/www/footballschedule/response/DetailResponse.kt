package com.kisaa.www.footballschedule.response

import com.kisaa.www.footballschedule.model.Detail

data class DetailResponse (
    val leagues: List<Detail>
)