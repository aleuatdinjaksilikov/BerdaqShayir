package com.aleuatdinjaksilikov.berdaqshayir.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.aleuatdinjaksilikov.berdaqshayir.data.model.SongData

@Dao
interface SongsDao {

    @Query("SELECT * FROM songs_table WHERE lan=:lan")
    suspend fun getSongs(lan : String):List<SongData>

    @Query("SELECT song FROM songs_table WHERE id=:id")
    suspend fun getSongById(id:Int):String
}