package com.hucet.github.repository

import com.hucet.github.api.GithubApi
import com.hucet.github.debug.OpenForTesting
import javax.inject.Inject
import javax.inject.Singleton

@OpenForTesting
@Singleton
class RepoRepository @Inject constructor(
    private val api: GithubApi
) {
//    fun searchRepos(query: String): LiveData<ApiResponse<RepoSearchResponse>> {
//        return api.searchRepos(query)
//    }
//    fun searchRepos(query: String): LiveData<Resource<List<Repo>>> {
//        return
//    }
}