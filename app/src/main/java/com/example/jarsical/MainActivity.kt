package com.example.jarsical

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {
    var songCollection = SongCollection()
    val libraryFragment = com.example.jarsical.fragments.library.libraryFragment()
    val songFragment = com.example.jarsical.fragments.list.songFragment()
    val searchFragment = com.example.jarsical.fragments.search.searchFragment()


    //creates instance of database
    //val db = Room.databaseBuilder( applicationContext, UserDatabase::class.java, "song_list")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hides action bar
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        //sets songFragment to load onCreate
        replaceFragment(songFragment)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> replaceFragment(songFragment)
                R.id.nav_library -> replaceFragment(libraryFragment)
                R.id.nav_search -> replaceFragment(searchFragment)

            }
            true
        }


    }

    fun handleSelection(myView: View) {
        //reads contentDescription of image button and adds it to the val
        val contentDescription = myView.contentDescription.toString()

        //reads contentDescription of image button and adds it to the val
        val currentArrayIndex = songCollection.searchSongById(contentDescription)
        Log.d("Temasek", "The id of the pressed ImageButton is: $contentDescription")
        sendDataToActivity(currentArrayIndex)
    }

    fun sendDataToActivity(index: Int) {
        val intent = Intent(this, PlaySongActivity::class.java)
        intent.putExtra("index", index)
        startActivity(intent)
    }

    //replaceFragment facilitates changing container views on click
    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_Container, fragment)
            transaction.commit()
        }
    }


}