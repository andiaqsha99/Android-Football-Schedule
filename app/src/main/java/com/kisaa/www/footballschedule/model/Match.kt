package com.kisaa.www.footballschedule.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match(
    @SerializedName("strHomeTeam")
    val homeTeam: String? = null,

    @SerializedName("strAwayTeam")
    val awayTeam: String? = null,

    @SerializedName("intHomeScore")
    val homeScore: String? = null,

    @SerializedName("intAwayScore")
    val awayScore: String? = null,

    @SerializedName("idHomeTeam")
    val idHome: String? = null,

    @SerializedName("idAwayTeam")
    val idAway: String? = null,

    @SerializedName("dateEvent")
    val matchDate: String? = null,

    @SerializedName("strTime")
    val matchTime: String? = null,

    @SerializedName("strHomeRedCards")
    val homeRed: String? = null,

    @SerializedName("strHomeYellowCards")
    val homeYellow: String? = null,

    @SerializedName("strHomeGoalDetails")
    val homeGoalDetail: String? = null,

    @SerializedName("strHomeLineupGoalkeeper")
    val homeGoalkeeper: String? = null,

    @SerializedName("strHomeLineupDefense")
    val homeDefense: String? = null,

    @SerializedName("strHomeLineupMidfield")
    val homeMidfield: String? = null,

    @SerializedName("strHomeLineupForward")
    val homeFroward: String? = null,

    @SerializedName("strAwayRedCards")
    val awayRed: String? = null,

    @SerializedName("strAwayYellowCards")
    val awayYellow: String? = null,

    @SerializedName("strAwayGoalDetails")
    val awayGoalDetail: String? = null,

    @SerializedName("strAwayLineupGoalkeeper")
    val awayGoalkeeper: String? = null,

    @SerializedName("strAwayLineupDefense")
    val awayDefense: String? = null,

    @SerializedName("strAwayLineupMidfield")
    val awayMidfield: String? = null,

    @SerializedName("strAwayLineupForward")
    val awayFroward: String? = null,

    @SerializedName("idEvent")
    val idMatch: String? = null,

    @SerializedName("strSport")
    val typeSport: String? = null,

    @SerializedName("idLeague")
    val idLeague: String? = null,

    @SerializedName("strSeason")
    var season: String? = null
): Parcelable
