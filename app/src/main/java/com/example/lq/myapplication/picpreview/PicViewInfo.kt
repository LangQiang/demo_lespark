package com.example.lq.myapplication.picpreview

import android.os.Parcel
import android.os.Parcelable
import android.view.View

/**
 * Created by lq on 2018/5/20.
 */
data class PicViewInfo(var mUrl: Int,
                       var mRawX: Float,
                       var mRawY: Float,
                       var mWidth : Int,
                       var mHeight : Int,
                       var mCurrentSelect: Boolean = false) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readFloat(),
            parcel.readFloat(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(mUrl)
        parcel.writeFloat(mRawX)
        parcel.writeFloat(mRawY)
        parcel.writeInt(mWidth)
        parcel.writeInt(mHeight)
        parcel.writeByte(if (mCurrentSelect) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PicViewInfo> {
        override fun createFromParcel(parcel: Parcel): PicViewInfo {
            return PicViewInfo(parcel)
        }

        override fun newArray(size: Int): Array<PicViewInfo?> {
            return arrayOfNulls(size)
        }
    }

}