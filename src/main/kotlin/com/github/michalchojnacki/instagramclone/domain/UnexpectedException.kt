package com.github.michalchojnacki.instagramclone.domain

class UnexpectedException(e: Throwable) : Exception("Oops! Something unexpected happened", e)