package com.vibs.assignsong.database

import androidx.room.Database

@Database(entities = arrayOf(SongEntity::class), version = 1)
abstract class SongDataBase {
    abstract fun songDao(): SongDao
}