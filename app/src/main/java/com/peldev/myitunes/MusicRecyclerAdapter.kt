package com.peldev.myitunes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peldev.myitunes.R
import com.peldev.myitunes.Music

class MusicRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<MusicRecyclerAdapter.ViewHolder>() {

    var musicList: List<Music>? = null

    fun setMusics(musicList: List<Music>) {
        this.musicList = musicList
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val musicImage: ImageView = itemView.findViewById(R.id.music_image)
        val musicTitle: TextView = itemView.findViewById(R.id.music_title)
    }

    override fun getItemViewType(position: Int) = position % 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutId = R.layout.mi_staggered_music_card_first
        if (viewType == 1) {
            layoutId = R.layout.mi_staggered_music_card_second
        } else if (viewType == 2) {
            layoutId = R.layout.mi_staggered_music_card_third
        }

        val layoutView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(layoutView)
    }

    override fun getItemCount() = if (musicList == null)  0 else musicList?.size!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (musicList != null && position < musicList?.size!!) {
            holder.musicTitle.text = musicList!![position].name
            musicList!![position].artWork.let {
                Glide.with(context).load(it).into(holder.musicImage)
            }
        }

    }

}