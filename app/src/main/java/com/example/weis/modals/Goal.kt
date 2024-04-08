package com.example.weis.modals

import android.os.Parcel
import android.os.Parcelable

data class Goal(
    var id: String? = null,
    val goal: String,
    val duration: Long,
    var date: String? = null,
    var time: String? = null,
    var open: Boolean = false,
    var timestamp: Long? = null
): Parcelable {
    // Implement Parcelable methods here
    // For example:
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(goal)
        parcel.writeLong(duration)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeByte(if (open) 1 else 0)
    }
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Goal> {
        override fun createFromParcel(parcel: Parcel): Goal {
            return Goal(parcel)
        }

        override fun newArray(size: Int): Array<Goal?> {
            return arrayOfNulls(size)
        }
    }
}