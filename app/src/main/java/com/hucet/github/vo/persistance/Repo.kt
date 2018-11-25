package com.hucet.github.vo.persistance

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity(
    indices = [
        Index("id"),
        Index("owner_login")],
    primaryKeys = ["name", "owner_login"]
)
@Parcelize
data class Repo(
    val id: Int,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "full_name")
    val fullName: String,
    @field:Json(name = "description")
    val description: String?,
    @field:Json(name = "owner")
    @field:Embedded(prefix = "owner_")
    val owner: Owner,
    @field:Json(name = "stargazers_count")
    val stars: Int
) : Parcelable {

    @Parcelize
    data class Owner(
        @field:Json(name = "login")
        val login: String,
        @field:Json(name = "url")
        val url: String?
    ) : Parcelable

    companion object {
        const val UNKNOWN_ID = -1
    }
}