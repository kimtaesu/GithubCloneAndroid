package com.hucet.github.views.search

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hucet.github.R
import com.hucet.github.common.AppExecutors
import com.hucet.github.databinding.FragmentSearchRepoBinding
import com.hucet.github.debug.OpenForTesting
import com.hucet.github.di.Injectable
import com.hucet.github.utils.get
import com.hucet.github.views.common.BindingFragment
import com.hucet.github.vo.Status
import com.jakewharton.rxbinding3.widget.editorActions
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_search_repo.search
import timber.log.Timber
import javax.inject.Inject

@OpenForTesting
class SearchRepoFragment : BindingFragment<FragmentSearchRepoBinding>(), Injectable, SearchRepoView {
    override val bindResId: Int = R.layout.fragment_search_repo

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var appExecutors: AppExecutors

    private val viewModel by lazy {
        viewModelFactory.get<SearchRepoViewModel>(this)
    }

    private val adapter by lazy {
        SearchRepoAdapter(appExecutors) {
            //            TODO
            Timber.i("clicked at item $it")
        }
    }

    override fun searchAction(): Observable<Int> = search.editorActions()
    override fun queryChanges(): Observable<CharSequence> = search.textChanges()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.bind(this)

        binding.listSearch.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@SearchRepoFragment.adapter
        }

        viewModel.results.observe(this, Observer {
            Timber.i("search: [${it}]")
            when (it.status) {
                Status.SUCCESS -> {
                    adapter.submitList(it.data)
                }
                Status.ERROR -> {
                    AlertDialog
                            .Builder(requireContext())
                            .setTitle("에러")
                            .setMessage(it.message)
                            .setPositiveButton(android.R.string.ok, null)
                            .show()
                }
                Status.LOADING -> {

                }
            }
        })
    }
}