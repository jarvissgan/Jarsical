package com.example.jarsical.fragments.library

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jarsical.R
import com.example.jarsical.Song
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_library.view.*

class libraryFragment : Fragment(){
    var sharedPreferences = SharedPreferences
    var favList: ArrayList<Song> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_library, container, false)

        var albums = sharedPreferences.getString("favList","")

        if(!albums.equals("")){
            var token = object : TypeToken<ArrayList<Song>>(){}.type
            var gson = Gson()
            favList = gson.fromJson(albums,token)
            Log.d("libraryAdapter","IT WORKS JARVIS")
        }

        //recyclerview
        val adapter = libraryAdapter(favList)
        val recyclerView = view.recyclerViewLibrary
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view

    }
}