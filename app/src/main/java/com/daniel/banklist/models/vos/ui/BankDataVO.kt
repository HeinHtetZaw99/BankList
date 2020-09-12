package com.daniel.banklist.models.vos.ui

import android.os.Parcel
import android.os.Parcelable

data class BankDataVO(

	var instruction2: String = "",

	var endColor: String = "",

	var instruction1: String = "",

	var length: Int = 0,

	var logoThumbnail: String = "",

	var description: String = "",

	var type: String = "",

	var logoUrl: String = "",

	var enabled: Int = 0,

	var startColor: String = "",

	var centerColor: String = "",

	var bgColor: String = "",

	var isMajor: Boolean = false,

	var name: String = "",

	var id: Int = 0,

	var maxAmount: String = "",

	var fontColor: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
		parcel.readString() ?: "",
		parcel.readString() ?: "",
		parcel.readString() ?: "",
		parcel.readInt() ?: 0,
		parcel.readString() ?: "",
		parcel.readString() ?: "",
		parcel.readString() ?: "",
		parcel.readString() ?: "",
		parcel.readInt() ?: 0,
		parcel.readString() ?: "",
		parcel.readString() ?: "",
		parcel.readString() ?: "",
		parcel.readByte() != 0.toByte() ?: "",
		parcel.readString() ?: "",
		parcel.readInt() ?: 0,
		parcel.readString() ?: "",
		parcel.readString() ?: ""
	) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(instruction2)
        parcel.writeString(endColor)
        parcel.writeString(instruction1)
        parcel.writeInt(length)
        parcel.writeString(logoThumbnail)
        parcel.writeString(description)
        parcel.writeString(type)
        parcel.writeString(logoUrl)
        parcel.writeInt(enabled)
        parcel.writeString(startColor)
        parcel.writeString(centerColor)
        parcel.writeString(bgColor)
        parcel.writeByte(if (isMajor) 1 else 0)
        parcel.writeString(name)
        parcel.writeInt(id)
        parcel.writeString(maxAmount)
        parcel.writeString(fontColor)
    }

    override fun describeContents(): Int {
        return 0
    }

	override fun toString(): String {
		return "BankDataVO(instruction2='$instruction2', endColor='$endColor', instruction1='$instruction1', length=$length, logoThumbnail='$logoThumbnail', description='$description', type='$type', logoUrl='$logoUrl', enabled=$enabled, startColor='$startColor', centerColor='$centerColor', bgColor='$bgColor', isMajor=$isMajor, name='$name', id=$id, maxAmount='$maxAmount', fontColor='$fontColor')"
	}

	companion object CREATOR : Parcelable.Creator<BankDataVO> {
        override fun createFromParcel(parcel: Parcel): BankDataVO {
            return BankDataVO(parcel)
        }

        override fun newArray(size: Int): Array<BankDataVO?> {
            return arrayOfNulls(size)
        }
    }


}