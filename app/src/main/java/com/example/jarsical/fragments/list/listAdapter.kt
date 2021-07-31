package com.example.jarsical.fragments.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jarsical.R
import com.example.jarsical.R.layout.custom_row
import com.example.jarsical.SongCollection
import com.example.jarsical.data.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_row.view.*

class listAdapter: RecyclerView.Adapter<listAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()
    var songCollection = SongCollection()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(custom_row, parent, false))
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
        holder.itemView.imageButton.contentDescription = currentItem.id
        val test = currentItem.id
        Log.d("onBind",test)

    }

    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }


}