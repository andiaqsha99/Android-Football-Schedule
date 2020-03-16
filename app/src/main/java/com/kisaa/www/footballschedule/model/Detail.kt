package com.kisaa.www.footballschedule.model

import com.google.gson.annotations.SerializedName

data class Detail (
    @SerializedName("strLeague")
    var title: String? = null,

    @SerializedName("strCountry")
    var country: String? = null,

    @SerializedName("strWebsite")
    var website: String? = null,

    @SerializedName("dateFirstEvent")
    var firstEvent: String? = null,

    @SerializedName("strBadge")
    var logo: String? = null
)