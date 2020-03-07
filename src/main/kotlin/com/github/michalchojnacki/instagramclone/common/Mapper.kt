package com.github.michalchojnacki.instagramclone.common

interface Mapper<T, V> {
    fun map(input : T) : V
}