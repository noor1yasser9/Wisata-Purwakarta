package com.nurbk.ps.informationindonesya.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class Hotel(
    @SerializedName("hotel")
    var hotel: List<HotelContent>
)

@Parcelize
data class HotelContent(
    @SerializedName("alamat")
    var alamat: String,
    @SerializedName("gambar_url")
    var gambarUrl: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("kordinat")
    var kordinat: String,
    @SerializedName("nama")
    var nama: String,
    @SerializedName("nomor_telp")
    var nomorTelp: String
) : Parcelable