package com.akjaw.fullerstack.model

import android.os.Parcelable
import com.soywiz.klock.DateTime
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParcelableNote(
    val title: String = "",
    val content: String = "",
    val creationDate: DateTime = DateTime.now()
): Parcelable
