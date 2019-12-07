package com.github.michalchojnacki.instagramclone.domain.model

import java.lang.Exception

class UserAlreadyExistsException : Exception("User already exists!")