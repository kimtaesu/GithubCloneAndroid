package com.hucet.github.views.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.hucet.github.R
import com.hucet.github.common.AppExecutors
import com.hucet.github.databinding.RepoItemBinding
import com.hucet.github.types.ListItemClickListener
import com.hucet.github.views.common.DataBoundListAdapter
import com.hucet.github.vo.persistance.Repo

private typealias ITEM = Repo

class SearchRepoAdapter constructor(
    appExecutors: AppExecutors,
    private val onClickListener: ListItemClickListener<ITEM>
) : DataBoundListAdapter<ITEM, RepoItemBinding>(appExecutors, diff) {

    companion object {
        val diff = object : DiffUtil.ItemCallback<ITEM>() {
            override fun areContentsTheSame(oldItem: ITEM, newItem: ITEM): Boolean =
                    oldItem.description == newItem.description && oldItem.stars == newItem.stars

            override fun areItemsTheSame(oldItem: ITEM, newItem: ITEM): Boolean =
                    oldItem.owner == newItem.owner && oldItem.name == newItem.name
        }
    }

    override fun createBinding(parent: ViewGroup, viewType: Int): RepoItemBinding {
        val binding = DataBindingUtil.inflate<RepoItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.repo_item,
            parent,
            false
        )
        binding.root.setOnClickListener {
            binding.repo?.let {
                onClickListener?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: RepoItemBinding, item: ITEM) {
        binding.repo = item
    }
}