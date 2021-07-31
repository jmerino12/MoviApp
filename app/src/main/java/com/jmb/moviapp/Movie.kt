package com.jmb.moviapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(val name: String) : Parcelable
