package com.example.jarsical.fragments.library

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.jarsical.R
import com.example.jarsical.R.layout.custom_row2
import com.example.jarsical.Song
import com.example.jarsical.fragments.library.libraryAdapter.MyViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_row.view.*

class libraryAdapter(favList: ArrayList<Song>): Adapter<MyViewHolder>()  {

    var favList: ArrayList<Song> = ArrayList()

    class MyViewHolder(itemView: View): ViewHolder(itemView){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(custom_row2, parent, false))

    }

    override fun getItemCount(): Int {
        return favList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = favList[position]

        holder.itemView.txtSongName.text = currentItem.title
        holder.itemView.txtSongArtist.text = currentItem.artists
        holder.itemView.txtSongLength.text = currentItem.songLength.toString()

        //picasso allows us to use cover art without downloading
        Picasso.get().load(currentItem.artLink).placeholder(R.drawable.ic_baseline_error_24).into(holder.itemView.imageButton)// loads images into imageButton
        //sets content description
        holder.itemView.imageButton.contentDescription = currentItem.id
        val test = currentItem.id
        Log.d("libraryAdapter",test)

    }

    fun setData(song: ArrayList<Song>){
        this.favList = song
        notifyDataSetChanged()
        Log.d("setData",favList.size.toString())

    }
}