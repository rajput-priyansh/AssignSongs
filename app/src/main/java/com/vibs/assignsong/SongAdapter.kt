package com.vibs.assignsong

import androidx.recyclerview.widget.RecyclerView

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import android.widget.TextView
import com.vibs.assignsong.database.SongEntity


class SongAdapter(private val songs: ArrayList<SongEntity>, private val mCallBack: SongSelection) :
    RecyclerView.Adapter<SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(parent).bind()
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.tvSongname.text = songs[position].songName
        holder.ivFav.setBackgroundResource(if (songs[position].isFav == 0) R.drawable.ic_baseline_favorite_border_24 else R.drawable.ic_baseline_favorite_24)

        holder.ivFav.setOnClickListener {
            mCallBack.onSongSelect(songs[position])
        }

    }

    override fun getItemCount(): Int {
        return songs.size
    }

}

class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    lateinit var tvSongname: TextView
    lateinit var ivFav: ImageView
    fun bind(): SongViewHolder {
        tvSongname = itemView.findViewById(R.id.tvSongName)
        ivFav = itemView.findViewById(R.id.ivSelected)

        return this
    }
}