package com.daniel.banklist.activities

import android.os.Bundle
import android.view.View
import com.daniel.banklist.R
import com.daniel.banklist.adapters.BankListAdapter
import com.daniel.banklist.configure
import com.daniel.banklist.delegate.BankDataListDelegate
import com.daniel.banklist.models.vos.ReturnResult
import com.daniel.banklist.models.vos.ui.BankDataVO
import com.daniel.banklist.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : BaseActivity<MainViewModel>(), BankDataListDelegate {
    private val adapter: BankListAdapter by lazy { BankListAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }

    override val rootLayout: View? by lazy { mainRootLayout }

    override val viewModel: MainViewModel by viewModels()

    override fun loadData() {
        viewModel.getBankList()
    }

    override fun initUI() {
        loadData()

        bankListRv.configure(this, adapter)

        viewModel.getBankListLiveData().observe(this, {
            swipeRefreshLayout.isRefreshing = false
            if (it is ReturnResult.PositiveResult) {
                adapter.appendNewData(it.data)
            } else {
                showSnackBar(rootLayout!!, it)
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }
    }

    override fun onClickedBank(data: BankDataVO) {
        startActivity(BankDetailsActivity.newIntent(this, data))
    }
}