package com.example.jarsical.fragments.add

import android.os.Bundle
import android.text.Editable
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
        val username = addFirstName.text.toString()
        val password = addLastName.text.toString()
        val age = addAge.text

        if(inputCheck(username,password,age)){
            //creates objects
            val user = User(0,username,password,Integer.parseInt(age.toString()))
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
    private fun inputCheck(username: String,password: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(username) && TextUtils.isEmpty(password) && age.isEmpty())

    }
}