package com.daniel.banklist.viewmodels

import androidx.lifecycle.MutableLiveData
import com.daniel.banklist.R
import com.daniel.banklist.components.interfaces.GenericErrorMessageFactory
import com.daniel.banklist.components.rx.PostExecutionThread
import com.daniel.banklist.components.rx.ThreadExecutor
import com.daniel.banklist.models.vos.ReturnResult
import com.daniel.banklist.models.vos.ui.BankDataVO
import com.daniel.banklist.repositories.BankListRepository
import com.daniel.banklist.showLogE
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val genericErrorMessageFactory: GenericErrorMessageFactory,
    private val bankListRepository: BankListRepository,
    postExecutionThread: PostExecutionThread,
    threadExecutor: ThreadExecutor
) : BaseViewModel(postExecutionThread, threadExecutor) {

    private val bankListLD : MutableLiveData<ReturnResult<List<BankDataVO>>> by lazy { MutableLiveData() }

    fun getBankListLiveData() = bankListLD

    fun getBankList(){
        bankListRepository.getAllBanks()
            .handle()
            .subscribe({
                bankListLD.postValue(ReturnResult.PositiveResult(it))
            },{
                showLogE("Error in getBankList", it )
                bankListLD.postValue(genericErrorMessageFactory.getError(it, R.string.error_loading_data))
            }).addToCompositeDisposable()
    }

}