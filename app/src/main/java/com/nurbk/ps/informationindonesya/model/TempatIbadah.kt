package com.nurbk.ps.informationindonesya.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class TempatIbadah(
    @SerializedName("tempat_ibadah")
    var tempatIbadah: List<TempatIbadahContent>
)

@Parcelize
data class TempatIbadahContent(
    @SerializedName("id")
    var id: Int,
    @SerializedName("jenis")
    var jenis: String,
    @SerializedName("latitude")
    var latitude: String,
    @SerializedName("longitude")
    var longitude: String,
    @SerializedName("nama")
    var nama: String
) : Parcelable