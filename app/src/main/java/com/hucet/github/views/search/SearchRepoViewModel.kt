package com.hucet.github.views.search

import android.view.inputmethod.EditorInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.hucet.github.debug.OpenForTesting
import com.hucet.github.repository.RepoRepository
import com.hucet.github.utils.AbsentLiveData
import com.hucet.github.views.common.DisposableViewModel
import com.hucet.github.vo.Resource
import com.hucet.github.vo.persistance.Repo
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom
import javax.inject.Inject

interface SearchRepoView {
    fun searchAction(): Observable<Int>
    fun queryChanges(): Observable<CharSequence>
}

@OpenForTesting
class SearchRepoViewModel @Inject constructor(
    private val repository: RepoRepository
) : DisposableViewModel() {

    private val query = MutableLiveData<String>()

    val results: LiveData<Resource<List<Repo>>> = Transformations
            .switchMap(query) { search ->
                if (search.isNullOrBlank()) {
                    AbsentLiveData.create()
                } else {
                    repository.search(search)
                }
            }

    fun bind(view: SearchRepoView) {
        view.searchAction()
                .filter { EditorInfo.IME_ACTION_SEARCH == it }
                .withLatestFrom(view.queryChanges())
                .subscribe {
                    query.value = it.second.toString()
                }.also {
                    addToDispose(it)
                }
    }
}