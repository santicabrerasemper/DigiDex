package com.example.digidex.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Digimon(
    val name: String,
    val img: String,
    val level: String,
    var isFavorite: Boolean = false
) : Parcelable
