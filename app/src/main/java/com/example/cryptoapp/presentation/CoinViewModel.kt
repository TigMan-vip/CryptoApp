package com.example.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.data.repository.CoinRepositoryImpl
import com.example.cryptoapp.domain.GetCoinInfoListUseCase
import com.example.cryptoapp.domain.GetCoinInfoUseCase
import com.example.cryptoapp.domain.LoadDataUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    getCoinInfoListUseCase: GetCoinInfoListUseCase,
    loadDataUseCase: LoadDataUseCase,
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
): ViewModel() {

    val coinInfoList = getCoinInfoListUseCase.invoke()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase.invoke(fSym)

    init {
            loadDataUseCase.invoke()
    }
}
