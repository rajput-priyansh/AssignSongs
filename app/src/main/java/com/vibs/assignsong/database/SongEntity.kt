package com.vibs.assignsong.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SongEntity (
    @PrimaryKey
    val uid: Int,
    @ColumnInfo(name = "song_name") val songName: String?,
    @ColumnInfo(name = "is_fav") var isFav: Int?
)