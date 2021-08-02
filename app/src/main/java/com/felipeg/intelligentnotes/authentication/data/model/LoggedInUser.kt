package com.felipeg.intelligentnotes.authentication.data.model

data class LoggedInUser(
    val userId: String,
    val username: String,
    val jwtToken: String
)