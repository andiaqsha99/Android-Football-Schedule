package com.kisaa.www.footballschedule.model

data class FavoriteTeam(
    val id: Long?,
    val idTeam: String?,
    val teamName: String?,
    val teamBadge: String?,
    val teamDesc: String?
) {
    companion object{
        const val TABLE_TEAM: String = "TABLE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
        const val TEAM_DESCRIPTION: String = "TEAM_DESCRIPTION"
    }
}