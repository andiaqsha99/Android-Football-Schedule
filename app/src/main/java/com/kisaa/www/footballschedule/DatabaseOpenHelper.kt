package com.kisaa.www.footballschedule

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.kisaa.www.footballschedule.model.Favorite
import com.kisaa.www.footballschedule.model.FavoriteTeam
import org.jetbrains.anko.db.*

class DatabaseOpenHelper(ctx: Context):
    ManagedSQLiteOpenHelper(ctx, "Favorite.db", null, 1) {

    companion object{
        private var instance: DatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseOpenHelper{
            if (instance == null){
                instance = DatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as DatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            Favorite.TABLE_FAVORITE, true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.MATCH_ID to TEXT + UNIQUE,
            Favorite.HOME_ID to TEXT,
            Favorite.HOME_NAME to TEXT,
            Favorite.HOME_BADGE to TEXT,
            Favorite.HOME_SCORE to TEXT,
            Favorite.AWAY_ID to TEXT,
            Favorite.AWAY_NAME to TEXT,
            Favorite.AWAY_BADGE to TEXT,
            Favorite.AWAY_SCORE to TEXT,
            Favorite.MATCH_CLOCK to TEXT,
            Favorite.MATCH_DATE to TEXT)

        db?.createTable(
            FavoriteTeam.TABLE_TEAM, true,
            FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.TEAM_BADGE to TEXT,
            FavoriteTeam.TEAM_DESCRIPTION to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Favorite.TABLE_FAVORITE, true)
        db?.dropTable(FavoriteTeam.TABLE_TEAM, true)
    }
}
val Context.database: DatabaseOpenHelper
    get() = DatabaseOpenHelper.getInstance(applicationContext)
