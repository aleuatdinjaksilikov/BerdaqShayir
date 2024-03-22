package com.aleuatdinjaksilikov.berdaqshayir.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aleuatdinjaksilikov.berdaqshayir.data.dao.PoetInfoDao
import com.aleuatdinjaksilikov.berdaqshayir.data.dao.SongsDao
import com.aleuatdinjaksilikov.berdaqshayir.data.model.PoetData
import com.aleuatdinjaksilikov.berdaqshayir.data.model.SongData

@Database(entities = [PoetData::class, SongData::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            INSTANCE?.let {
                return it
            }

            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "BShdatabase.db"
            )
                .createFromAsset("BShdatabase.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

            INSTANCE = db
            return db
        }
    }
    abstract fun getSongs():SongsDao
    abstract fun getPoetInfo():PoetInfoDao
}