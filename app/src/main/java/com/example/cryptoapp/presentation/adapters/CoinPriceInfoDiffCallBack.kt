package com.example.cryptoapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.example.cryptoapp.data.network.model.CoinInfoDto

class CoinPriceInfoDiffCallBack: ItemCallback<CoinInfoDto>() {
    override fun areItemsTheSame(oldItem: CoinInfoDto, newItem: CoinInfoDto): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
     }

    override fun areContentsTheSame(oldItem: CoinInfoDto, newItem: CoinInfoDto): Boolean {
        return oldItem == newItem
    }
}