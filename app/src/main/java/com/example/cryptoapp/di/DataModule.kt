package com.example.cryptoapp.di

import android.app.Application
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinInfoDao
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideCoinInfoDao(application: Application): CoinInfoDao {
        return AppDatabase.getInstance(application).coinInfoDao()
    }
}