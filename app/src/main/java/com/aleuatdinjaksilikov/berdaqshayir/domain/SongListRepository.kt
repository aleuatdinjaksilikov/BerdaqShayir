package com.aleuatdinjaksilikov.berdaqshayir.domain

import com.aleuatdinjaksilikov.berdaqshayir.data.dao.SongsDao
import com.aleuatdinjaksilikov.berdaqshayir.utils.ResultData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class SongListRepository(private val songsDao: SongsDao) {

    suspend fun getAllSongs(lan:String) = flow{
        val songslist = songsDao.getSongs(lan)
        if (songslist.isNotEmpty()){
            emit(ResultData.Success(songslist))
        }else{
            emit(ResultData.Message("Songs null"))
        }
    }.catch {
        emit(ResultData.Error(it))
    }

    suspend fun getSongById(id:Int) = flow{
        val song = songsDao.getSongById(id)
        if (song.isNotEmpty()){
            emit(ResultData.Success(song))
        }else{
            emit(ResultData.Message("Song null"))
        }
    }.catch {
        emit(ResultData.Error(it))
    }

}