package com.kotlin.photorx2.mvp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FileInfo(
    val inPath: String,
    val outPath: String
) : Parcelable
