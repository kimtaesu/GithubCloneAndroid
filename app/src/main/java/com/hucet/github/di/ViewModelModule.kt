package com.hucet.github.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hucet.github.utils.ViewModelFactory
import com.hucet.github.views.search.SearchRepoViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelModule.ViewModelKey(SearchRepoViewModel::class)
    internal abstract fun bindSearchRepoViewModel(viewModel: SearchRepoViewModel): ViewModel

    @MustBeDocumented
    @Retention(AnnotationRetention.RUNTIME)
    @MapKey
    annotation class ViewModelKey(val value: KClass<out ViewModel>)
}