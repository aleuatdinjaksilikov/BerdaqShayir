package com.aleuatdinjaksilikov.berdaqshayir.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.aleuatdinjaksilikov.berdaqshayir.data.model.PoetData

@Dao
interface PoetInfoDao {

    @Query("SELECT * FROM poet_info WHERE id=:id")
    suspend fun getPoetInfo(id:Int):PoetData
}