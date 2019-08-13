package com.example.films.data.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ProfileOption(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
)
