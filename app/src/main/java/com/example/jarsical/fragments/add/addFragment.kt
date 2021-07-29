package com.example.jarsical.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.jarsical.R
import com.example.jarsical.data.User
import com.example.jarsical.data.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*



class addFragment : Fragment() {
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel ::class.java)

        view.btnAdd.setOnClickListener{
            insertDataToDatabase()
        }
        return view

    }

    private fun insertDataToDatabase() {
        val songName = addSongName.text.toString()
        val songArtist = addArtist.text.toString()
        val songLength = addSongLength.text
        val songLink = addSongLink.text.toString()
        val artLink = addArtLink.text.toString()

        if(inputCheck(songName,songArtist,songLink, artLink)){
            //creates objects
            val user = User(0,songName,songArtist,Integer.parseInt(songLength.toString()),songLink, artLink)
            //Add data to the database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(),"Successfully Added!",Toast.LENGTH_LONG).show()
            //navigate back to previous page
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Please fill up the boxes",Toast.LENGTH_LONG).show()

        }
    }
    //checks if fields are empty
    private fun inputCheck(songName: String, songArtist: String, songLink: String, artLink: String): Boolean{
        return !(TextUtils.isEmpty(songName) && TextUtils.isEmpty(songArtist) && TextUtils.isEmpty(songLink) && TextUtils.isEmpty(artLink))

    }
}