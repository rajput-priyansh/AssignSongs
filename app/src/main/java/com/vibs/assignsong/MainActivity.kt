package com.vibs.assignsong

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vibs.assignsong.database.SongDao
import com.vibs.assignsong.util.AppFile
import com.vibs.assignsong.util.DatabaseUtils

import android.provider.MediaStore
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vibs.assignsong.database.SongEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface SongSelection {
    fun onSongSelect(song: SongEntity)
}
class MainActivity : AppCompatActivity(), SongSelection {
    lateinit var appFile: AppFile
    lateinit var songDao: SongDao

    private lateinit var rvSongs: RecyclerView
    private lateinit var adpSong: SongAdapter

    private val allSong = ArrayList<SongEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()

        initUi()

        observer()
    }

    private fun initData() {
        appFile = AppFile().getInstance(this@MainActivity)
        songDao = DatabaseUtils().getInstance(this@MainActivity).getSongEntity()
        adpSong = SongAdapter(allSong, this@MainActivity)

        if (appFile.getInt(AppFile.KEY_FIRST_TIME) != -1) {
            GlobalScope.launch {
                addSongs()
            }
        }
    }

    private fun initUi() {

        rvSongs = findViewById(R.id.rvSongs)

        rvSongs.layoutManager = LinearLayoutManager(this@MainActivity)
        rvSongs.adapter = adpSong
    }

    private fun observer() {
        songDao.getAll().observe(this, {
            if (it == null)
                return@observe

            allSong.clear()
            allSong.addAll(it)
        })
    }

    private suspend fun addSongs() {
        //Some audio may be explicitly marked as not being music
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME
        )

        val cursor = managedQuery(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            null
        )

        val songs = ArrayList<SongEntity>()

        while (cursor.moveToNext()) {
            songs.add(SongEntity(cursor.getInt(0), cursor.getString(1), 0))
        }

        addAllSongsToDb(songs)

    }

    private suspend fun addAllSongsToDb(songs: ArrayList<SongEntity>) {
        withContext(Dispatchers.IO) {
            songDao.insertAll(songs)
        }
    }

    override fun onSongSelect(song: SongEntity) {
        //Make Update
        song.isFav = if(song.isFav == 0) 1 else 0

    }

}