package com.felipeg.intelligentnotes.data.model

data class LoggedInUser(
    val userId: String,
    val username: String,
    val jwtToken: String
)