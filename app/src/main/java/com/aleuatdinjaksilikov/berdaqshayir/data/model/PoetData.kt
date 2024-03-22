package com.aleuatdinjaksilikov.berdaqshayir.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "poet_info")
data class PoetData(
    @PrimaryKey val id : Int,
    val title:String,
    val lan :String
)
