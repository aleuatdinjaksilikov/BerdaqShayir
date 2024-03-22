package com.aleuatdinjaksilikov.berdaqshayir.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs_table")
data class SongData(
    @PrimaryKey val id:Int,
    val name: String,
    val lan : String,
    val song: String
)
