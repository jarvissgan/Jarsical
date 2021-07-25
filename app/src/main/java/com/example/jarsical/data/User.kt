package com.example.jarsical.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "song_list")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val songName: String,
    val songArtist: String,
    val songLength: Int,

)
