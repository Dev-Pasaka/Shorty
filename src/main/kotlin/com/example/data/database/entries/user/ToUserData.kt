package com.example.data.database.entries.user

import com.example.domain.model.user.UserData

fun User.toUserData(): UserData {
    return UserData(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phone = phone,
    )
}