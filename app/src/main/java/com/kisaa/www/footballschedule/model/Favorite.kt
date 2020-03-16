package com.kisaa.www.footballschedule.model

data class Favorite(
    val id: Long?,
    val matchId: String?,
    val idHome: String?,
    val homeName: String?,
    val homeBadge: String?,
    val homeScore: String?,
    val idAway: String?,
    val awayName: String?,
    val awayBadge: String?,
    val awayScore: String?,
    val matchTime: String?,
    val matchDate: String?){

    companion object{
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val MATCH_ID: String = "MATCH_ID"
        const val HOME_ID: String = "HOME_ID"
        const val HOME_NAME: String = "HOME_NAME"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val HOME_BADGE: String = "HOME_BADGE"
        const val AWAY_ID: String = "AWAY_ID"
        const val AWAY_NAME: String = "AWAY_NAME"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val AWAY_BADGE: String = "AWAY_BADGE"
        const val MATCH_CLOCK: String = "MATCH_CLOCK"
        const val MATCH_DATE: String = "MATCH_DATE"
    }
}