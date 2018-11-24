package com.hucet.github.vo

import com.hucet.github.db.Repo
import com.squareup.moshi.Json

data class RepoSearchResponse(
    @Json(name = "total_count")
    val total: Int = 0,
    @Json(name = "items")
    val items: List<Repo>
) {
    var nextPage: Int? = null
}