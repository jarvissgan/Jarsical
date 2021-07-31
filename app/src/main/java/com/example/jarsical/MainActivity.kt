package com.example.jarsical

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController


class MainActivity: AppCompatActivity() {
    var songCollection = SongCollection()

    //creates instance of database
    //val db = Room.databaseBuilder( applicationContext, UserDatabase::class.java, "song_list")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //hides action bar
        supportActionBar?.hide()

        setupActionBarWithNavController(findNavController(R.id.fragmentView))
    }

    fun handleSelection(myView: View) {
        //collects contentDescription from button pressed, value will be used in Room DB in PlaySongCollection
        val artLink = myView.contentDescription.toString()
        Log.d("handle",artLink)

        //val resourceId = resources.getResourceEntryName(myView.id)
        //val currentArrayIndex = songCollection.searchSongById(resourceId)
        //Log.d("Temasek", "The id of the pressed ImageButton is: $resourceId")

        sendDataToActivity(artLink)
    }

    fun sendDataToActivity(artlink: String) {
        val intent = Intent(this, PlaySongActivity::class.java)
        intent.putExtra("artLink", artlink)
        startActivity(intent)
        Log.d("sendData","works")

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController((R.id.fragmentView))
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}