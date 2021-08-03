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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //hides action bar
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)
        setupActionBarWithNavController(findNavController(R.id.fragmentView))
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController((R.id.fragmentView))
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}