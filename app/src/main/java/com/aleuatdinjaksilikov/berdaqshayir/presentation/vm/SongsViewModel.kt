package com.aleuatdinjaksilikov.berdaqshayir.presentation.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.aleuatdinjaksilikov.berdaqshayir.data.dao.SongsDao
import com.aleuatdinjaksilikov.berdaqshayir.data.db.AppDatabase
import com.aleuatdinjaksilikov.berdaqshayir.data.model.SongData
import com.aleuatdinjaksilikov.berdaqshayir.domain.SongListRepository
import com.aleuatdinjaksilikov.berdaqshayir.utils.ResultData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SongsViewModel(application: Application):AndroidViewModel(application) {

    private var dao : SongsDao
    private var repo : SongListRepository

    init {
        dao = AppDatabase.getInstance(application).getSongs()
        repo = SongListRepository(dao)
    }

    private val _getAllSongs = MutableSharedFlow<List<SongData>>()
    val getAllSongs: SharedFlow<List<SongData>> get() = _getAllSongs

    private val _messageFlow = MutableSharedFlow<String>()
    val messageFlow: SharedFlow<String> get() = _messageFlow

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow: SharedFlow<Throwable> get() = _errorFlow

    suspend fun getAllSongs(lan:String){
        repo.getAllSongs(lan).onEach {
            when(it){
                is ResultData.Success -> {
                    _getAllSongs.emit(it.data)
                }
                is ResultData.Message ->{
                    _messageFlow.emit(it.message)
                }
                is ResultData.Error ->{
                    _errorFlow.emit(it.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    private val _getSongById = MutableSharedFlow<String>()
    val getSongById: SharedFlow<String> get() = _getSongById

    private val _messageSong = MutableSharedFlow<String>()
    val messageSong: SharedFlow<String> get() = _messageSong

    private val _errorSong = MutableSharedFlow<Throwable>()
    val errorSong: SharedFlow<Throwable> get() = _errorSong

    suspend fun getSongById(id:Int){
        repo.getSongById(id).onEach {
            when(it){
                is ResultData.Success ->{
                    _getSongById.emit(it.data)
                }
                is ResultData.Message ->{
                    _messageSong.emit(it.message)
                }
                is ResultData.Error ->{
                    _errorSong.emit(it.error)
                }
            }
        }.launchIn(viewModelScope)
    }
}