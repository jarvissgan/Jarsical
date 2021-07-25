package com.example.jarsical.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jarsical.R.layout.custom_row
import com.example.jarsical.data.User
import kotlinx.android.synthetic.main.custom_row.view.*

class listAdapter: RecyclerView.Adapter<listAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(custom_row, parent, false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.itemView.txtSongName.text = currentItem.songName
        holder.itemView.txtSongArtist.text = currentItem.songArtist
        holder.itemView.txtSongLength.text = currentItem.songLength.toString()

    }

    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }


}