package com.daniel.banklist.mappers

interface BidirectionalMap<S, T> {

    fun map(data: S): T

    fun reverseMap(data: T): S

}