package com.daniel.banklist.viewmodels

import androidx.lifecycle.ViewModel
import com.daniel.banklist.components.rx.PostExecutionThread
import com.daniel.banklist.components.rx.ThreadExecutor
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


abstract class BaseViewModel(private val postExecutionThread: PostExecutionThread,
                             private val threadExecutor: ThreadExecutor
) : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


    fun Disposable.addToCompositeDisposable(): Disposable
            = apply { compositeDisposable.add(this) }

    fun <T> Observable<T>.handle(): Observable<T> {
        return this.subscribeOn(Schedulers.from(threadExecutor)).observeOn(
            postExecutionThread.scheduler
        )
    }

}
