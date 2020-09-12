package com.daniel.banklist.components.rx

import io.reactivex.Scheduler

interface PostExecutionThread {

    val scheduler: Scheduler

}