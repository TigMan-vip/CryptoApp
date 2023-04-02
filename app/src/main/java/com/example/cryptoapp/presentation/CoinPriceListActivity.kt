package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinPrceListBinding
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter
import kotlinx.android.synthetic.main.activity_coin_prce_list.*

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
            rvCoinPriceList.itemAnimator = null
        }
        setupClickListener()
    }

    private fun setupClickListener() {
        coinPriceListAdapter.onCoinClickListener = {
            if (binding.fragmentContainer == null) {
                launchCoinDetailActivity(it.fromSymbol)
            } else {
                launchCoinDetailFragment(it.fromSymbol)
            }
        }
    }

    private fun launchCoinDetailActivity(fromSymbol: String) {
        val intent = CoinDetailActivity.newIntent(this, fromSymbol)
        startActivity(intent)
    }

    private fun launchCoinDetailFragment(fromSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }

}
