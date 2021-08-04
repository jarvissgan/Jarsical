package com.example.jarsical

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {
    var songCollection = SongCollection()

    var favList: ArrayList<Song> = ArrayList()
    lateinit var sharedPreferences: SharedPreferences


    val libraryFragment = com.example.jarsical.fragments.library.libraryFragment()
    val songFragment = com.example.jarsical.fragments.list.songFragment()
    val searchFragment = com.example.jarsical.fragments.search.searchFragment()
    inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hides action bar
        supportActionBar?.hide()

        //creates sharedPreferences, MODE_PRIVATE makes its so
        // that it is only jarsical can access it
        sharedPreferences = getSharedPreferences("playlist", MODE_PRIVATE)
        var albums = sharedPreferences.getString("favList","")

        if(!albums.equals("")){
            var token = object : TypeToken<ArrayList<Song>>(){}.type
            var gson = Gson()
            favList = gson.fromJson(albums,token)
            Log.d("libraryAdapter","IT WORKS JARVIS")
        }


        setContentView(R.layout.activity_main)
        //sets songFragment to load onCreate
        replaceFragment(songFragment)

        //changes fragments based onClick
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> replaceFragment(songFragment)
                R.id.nav_library -> replaceFragment(libraryFragment)
                R.id.nav_search -> replaceFragment(searchFragment)
            }
            true
        }
    }

    //replaceFragment facilitates changing container views on click
    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_Container, fragment)
            transaction.commit()
        }
    }

    fun addToFavourite(view: View){
        val songID = view.contentDescription.toString()
        Log.d("gson add to fav",songID)

        val currentIndex = songCollection.searchSongById(songID)
        favList.add(songCollection.getCurrentSong(currentIndex))

        var gson =  Gson()
        var json = gson.toJson(favList)

        var editor = sharedPreferences.edit()
        editor.putString("favList",json)
        //saves json to system files
        editor.commit()


        Log.d("gson_add",json)
        Log.d("gson_add",favList.size.toString())

        var token = object: TypeToken<ArrayList<Song>>(){}.type
        var album = sharedPreferences.getString("favList","")
        favList = gson.fromJson(album,token)

    }

    fun goToFavouriteActivity(view: View){
        val intent = Intent(this, com.example.jarsical.fragments.library.libraryFragment::class.java)
        startActivity(intent)
        TODO("video part 1 - 30:21")

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




}