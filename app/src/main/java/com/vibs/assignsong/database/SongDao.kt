package com.vibs.assignsong.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SongDao {
    @Insert
    fun insertAll(songs: ArrayList<SongEntity>)

    @Query("SELECT * FROM songentity")
    fun getAll(): LiveData<List<SongEntity>>
}