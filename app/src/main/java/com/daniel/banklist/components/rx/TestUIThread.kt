package com.daniel.banklist.components.rx


import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestUIThread @Inject constructor() : PostExecutionThread {

    override val scheduler: Scheduler
        get() = TestScheduler().also {
//            it.triggerActions()
        }

}