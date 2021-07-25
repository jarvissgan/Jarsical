package com.example.jarsical.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jarsical.R
import com.example.jarsical.data.UserViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_list, container, false)

        //recyclerview
        val adapter = listAdapter()
        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //UserViewModel
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, { user -> adapter.setData((user)) })

        view.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return view
    }
}