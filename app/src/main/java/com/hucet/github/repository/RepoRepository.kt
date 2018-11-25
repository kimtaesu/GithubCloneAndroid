package com.hucet.github.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.hucet.github.api.ApiSuccessResponse
import com.hucet.github.api.GithubApi
import com.hucet.github.common.AppExecutors
import com.hucet.github.db.GithubDabase
import com.hucet.github.db.RepoDao
import com.hucet.github.vo.persistance.Repo
import com.hucet.github.vo.persistance.RepoSearchResult
import com.hucet.github.debug.OpenForTesting
import com.hucet.github.utils.AbsentLiveData
import com.hucet.github.vo.RepoSearchResponse
import com.hucet.github.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@OpenForTesting
@Singleton
class RepoRepository @Inject constructor(
    private val db: GithubDabase,
    private val api: GithubApi,
    private val appExecutors: AppExecutors
) {
    private val repoDao: RepoDao = db.repoDao()


    fun search(query: String): LiveData<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, RepoSearchResponse>(appExecutors) {

            override fun saveCallResult(item: RepoSearchResponse) {
                val repoIds = item.items.map { it.id }
                val repoSearchResult = RepoSearchResult(
                    query = query,
                    repoIds = repoIds,
                    totalCount = item.total,
                    next = item.nextPage
                )
                db.beginTransaction()
                try {
                    repoDao.insert(item.items)
                    repoDao.insert(repoSearchResult)
                    db.setTransactionSuccessful()
                } finally {
                    db.endTransaction()
                }
            }

            override fun shouldFetch(data: List<Repo>?) = data == null

            override fun loadFromDb(): LiveData<List<Repo>> {
                return Transformations.switchMap(repoDao.search(query)) { searchData ->
                    if (searchData == null) {
                        AbsentLiveData.create()
                    } else {
                        repoDao.loadOrdered(searchData.repoIds)
                    }
                }
            }

            override fun createCall() = api.searchRepos(query)

            override fun processResponse(response: ApiSuccessResponse<RepoSearchResponse>)
                    : RepoSearchResponse {
                val body = response.body
                body.nextPage = response.nextPage
                return body
            }
        }.asLiveData()
    }
}