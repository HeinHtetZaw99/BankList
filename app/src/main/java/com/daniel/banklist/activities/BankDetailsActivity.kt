package com.daniel.banklist.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import com.daniel.banklist.R
import com.daniel.banklist.addBackNavButton
import com.daniel.banklist.databinding.ActivityBankDetailsBinding
import com.daniel.banklist.models.vos.ui.BankDataVO
import com.daniel.banklist.show
import com.daniel.banklist.showLogD
import com.daniel.banklist.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_bank_details.*


class BankDetailsActivity : BaseActivity<MainViewModel>() {
    lateinit var data : BankDataVO
    private lateinit var binding : ActivityBankDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bank_details)
        initUI()

    }

    override val rootLayout: View? by lazy { null }

    override val viewModel: MainViewModel by viewModels()

    override fun loadData() {

    }

    override fun initUI() {
        toolbar.addBackNavButton(this , R.drawable.ic_baseline_arrow_back_24)

        data= intent.getParcelableExtra(DATA)!!
        binding.bankData = data
        binding.bankImageView.show(data.logoUrl)
        showLogD("data : $data")
    }

    companion object{
        val DATA = "data"

        fun newIntent(context: Context, payload: BankDataVO)=  Intent(
            context,
            BankDetailsActivity::class.java
        ).apply {
                putExtra(DATA, payload)

        }
    }
}