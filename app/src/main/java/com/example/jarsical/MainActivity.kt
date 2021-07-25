package com.example.jarsical

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {
    var songCollection = SongCollection()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.fragmentView))
    }

    fun handleSelection(myView: View) {
        val resourceId = resources.getResourceEntryName(myView.id)
        val currentArrayIndex = songCollection.searchSongById(resourceId)
        Log.d("Temasek", "The id of the pressed ImageButton is: $resourceId")
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