package com.vibs.assignsong.util

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vibs.assignsong.database.SongDao
import com.vibs.assignsong.database.SongDataBase

class DatabaseUtils() {
    companion object {
        var databaseUtils: DatabaseUtils? = null
    }
    private lateinit var db : SongDataBase

    fun getInstance(context: Context): DatabaseUtils {
        if (databaseUtils == null) {
            databaseUtils = DatabaseUtils()
            db = Room.databaseBuilder(
                context,
                SongDataBase::class.java, "song-db"
            ).build()
        }
        return databaseUtils!!
    }

    fun getSongEntity(): SongDao {
        return db.songDao()
    }
}