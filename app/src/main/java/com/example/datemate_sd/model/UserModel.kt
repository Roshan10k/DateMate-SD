package com.example.datemate_sd.model

import android.os.Parcel
import android.os.Parcelable

data class UserModel(
    var UserId : String ="",
    var emailAddress:String ="",
    var password: String="",
    var name: String = "",
    var username: String ="",
    var phnNumber : String="",
    var dateOfBirth: String="",
    var address: String="",
    var gender: String = "",
    var interestedIn: String = "",
    var idealMatch: String = "",
    var imageurl: String = ""

    ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(UserId)
        parcel.writeString(emailAddress)
        parcel.writeString(password)
        parcel.writeString(name)
        parcel.writeString(username)
        parcel.writeString(phnNumber)
        parcel.writeString(dateOfBirth)
        parcel.writeString(address)
        parcel.writeString(gender)
        parcel.writeString(interestedIn)
        parcel.writeString(idealMatch)
        parcel.writeString(imageurl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }

}