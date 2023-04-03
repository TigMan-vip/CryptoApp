package com.example.cryptoapp.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiFactory
import kotlinx.coroutines.delay

class RefreshDataWorker(
    context: Context,
    parameters: WorkerParameters
) : CoroutineWorker(context, parameters) {

    private val mapper = CoinMapper()
    private val coinInfoDao = AppDatabase.getInstance(context).coinInfoDao()
    private val apiService = ApiFactory.apiService

    override suspend fun doWork(): Result {
        while (true) {
                val topCoins = apiService.getTopCoinsInfo(limit = 50)
                val fSyms = mapper.mapNamesListToString(topCoins)
                val jsonContainer = apiService.getFullPriceList(fSyms = fSyms)
                val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
                val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
                coinInfoDao.insertPriceList(dbModelList)
            delay(10000)
        }
    }

    companion object {
        const val REFRESH_WORK_NAME = "RefreshDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
           return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }
}