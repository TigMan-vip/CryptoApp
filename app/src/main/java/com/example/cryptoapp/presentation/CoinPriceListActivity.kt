package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.ActivityCoinPrceListBinding
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var coinPriceListAdapter: CoinInfoAdapter


    private val viewModel by lazy {
        ViewModelProvider(this).get(CoinViewModel::class.java)
    }

    private val binding by lazy {
        ActivityCoinPrceListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpRecyclerView()
        viewModel.coinInfoList.observe(this) {
            coinPriceListAdapter.submitList(it)
        }
    }

    private fun setUpRecyclerView() {
        val rvCoinPriceList = binding.rvCoinPriceList
        with(rvCoinPriceList) {
            coinPriceListAdapter = CoinInfoAdapter(application)
            adapter = coinPriceListAdapter
        }
        setupClickListener()
    }

    private fun setupClickListener() {
        coinPriceListAdapter.onCoinClickListener = {
            val intent = CoinDetailActivity.newIntent(this, it.fromSymbol)
            startActivity(intent)
        }
    }


}
