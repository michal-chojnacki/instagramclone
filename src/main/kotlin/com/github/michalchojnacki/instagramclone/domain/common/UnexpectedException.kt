package com.github.michalchojnacki.instagramclone.domain.common

class UnexpectedException(e: Throwable) : Exception("Oops! Something unexpected happened", e)