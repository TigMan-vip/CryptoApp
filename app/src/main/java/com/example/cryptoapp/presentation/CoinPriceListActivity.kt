package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinPrceListBinding
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter
import kotlinx.android.synthetic.main.activity_coin_prce_list.*
import javax.inject.Inject

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var coinPriceListAdapter: CoinInfoAdapter

    lateinit var viewModel: CoinViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by lazy {
        ActivityCoinPrceListBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as CoinApp).component
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpRecyclerView()
        viewModel = ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
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
