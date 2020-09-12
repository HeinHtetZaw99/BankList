package com.daniel.banklist.mappers

interface UnidirectionalMap<S, T> {

    fun map(data: S): T
}