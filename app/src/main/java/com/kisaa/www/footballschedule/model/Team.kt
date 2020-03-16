package com.kisaa.www.footballschedule.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team (
    @SerializedName("strTeamBadge")
    val teamBadge: String? = null,

    @SerializedName("idTeam")
    val teamId: String? = null,

    @SerializedName("strTeam")
    val teamName: String? = null,

    @SerializedName("intFormedYear")
    val teamFormed: String? = null,

    @SerializedName("strWebsite")
    val teamWebsite: String? = null,

    @SerializedName("strFacebook")
    val teamFacebook: String? = null,

    @SerializedName("strTwitter")
    val teamTwitter: String? = null,

    @SerializedName("strInstagram")
    val teamInstagram: String? = null,

    @SerializedName("strDescriptionEN")
    val teamDescription: String? = null,

    @SerializedName("strTeamBanner")
    val teamBanner: String? = null
): Parcelable