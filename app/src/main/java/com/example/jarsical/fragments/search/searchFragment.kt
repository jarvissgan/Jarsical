package com.example.jarsical.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jarsical.R

class searchFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_library, container, false)

        //recyclerview
        /*val adapter = songAdapter()
        val recyclerView = view.recyclerViewSong
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())*/

        return view
    }
}