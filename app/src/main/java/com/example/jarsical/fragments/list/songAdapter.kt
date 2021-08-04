package com.example.jarsical.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jarsical.R
import com.example.jarsical.R.layout.custom_row2
import com.example.jarsical.Song
import com.example.jarsical.SongCollection
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_row.view.*

class songAdapter: RecyclerView.Adapter<songAdapter.MyViewHolder>() {

    var songCollection = SongCollection()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(custom_row2, parent, false))
    }

    override fun getItemCount(): Int {
        return songCollection.songs.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = songCollection.songs[position]

        holder.itemView.txtSongName.text = currentItem.title
        holder.itemView.txtSongArtist.text = currentItem.artists
        holder.itemView.txtSongLength.text = currentItem.songLength.toString()
        //picasso allows us to use cover art without downloading
        Picasso.get().load(currentItem.artLink).placeholder(R.drawable.ic_baseline_error_24).into(holder.itemView.imageButton)// loads images into imageButton
        //sets content description
        holder.itemView.buttonTest.contentDescription = currentItem.id

        /*Log.d("test", holder.itemView.buttonTest.contentDescription.toString())*/


        val test = currentItem.id
        /*Log.d("onBind",test)*/

    }
    fun setData(song: Array<Song>){
        this.songCollection.songs = song
        notifyDataSetChanged()
    }
}