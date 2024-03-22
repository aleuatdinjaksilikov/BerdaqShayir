package com.aleuatdinjaksilikov.berdaqshayir.domain

import com.aleuatdinjaksilikov.berdaqshayir.data.dao.PoetInfoDao
import com.aleuatdinjaksilikov.berdaqshayir.utils.ResultData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PoetInfoRepository(private val poetInfoDao: PoetInfoDao) {

    suspend fun getPoetInfo(id:Int) = flow {
        val data = poetInfoDao.getPoetInfo(id)
        if (data!=null && data.title.isNotEmpty()){
            emit(ResultData.Success(data))
        }else{
            emit(ResultData.Message("No title"))
        }
    }.catch {
        emit(ResultData.Error(it))
    }
}