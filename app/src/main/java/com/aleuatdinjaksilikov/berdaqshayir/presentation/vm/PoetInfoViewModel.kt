package com.aleuatdinjaksilikov.berdaqshayir.presentation.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleuatdinjaksilikov.berdaqshayir.data.dao.PoetInfoDao
import com.aleuatdinjaksilikov.berdaqshayir.data.db.AppDatabase
import com.aleuatdinjaksilikov.berdaqshayir.data.model.PoetData
import com.aleuatdinjaksilikov.berdaqshayir.domain.PoetInfoRepository
import com.aleuatdinjaksilikov.berdaqshayir.utils.ResultData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PoetInfoViewModel(application: Application):AndroidViewModel(application){

    private val dao by lazy {
        AppDatabase.getInstance(application).getPoetInfo()
    }

    private val repo by lazy {
        PoetInfoRepository(dao)
    }

    private val _getPoetInfo = MutableSharedFlow<PoetData>()
    val getPoetInfo: SharedFlow<PoetData> get() = _getPoetInfo

    private val _messageFlow = MutableSharedFlow<String>()
    val messageFlow: SharedFlow<String> get() = _messageFlow

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow: SharedFlow<Throwable> get() = _errorFlow


    suspend fun getPoetInfo(id:Int){
        repo.getPoetInfo(id).onEach {
            when(it){
                is ResultData.Success ->{
                    _getPoetInfo.emit(it.data)
                }
                is ResultData.Message->{
                    _messageFlow.emit(it.message)
                }
                is ResultData.Error->{
                    _errorFlow.emit(it.error)
                }
            }

        }.launchIn(viewModelScope)
    }
}