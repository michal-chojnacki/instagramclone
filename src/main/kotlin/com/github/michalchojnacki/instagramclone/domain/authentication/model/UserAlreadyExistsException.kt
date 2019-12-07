package com.github.michalchojnacki.instagramclone.domain.authentication.model

import java.lang.Exception

class UserAlreadyExistsException : Exception("User already exists!")