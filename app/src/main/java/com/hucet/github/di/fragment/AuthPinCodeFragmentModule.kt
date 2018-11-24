package com.hucet.github.di.fragment

import androidx.appcompat.app.AppCompatActivity
import com.hucet.github.di.scopes.PerFragment
import com.hucet.github.views.search.SearchRepoFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module()
internal abstract class SearchRepoFragmentModule {
    @PerFragment
    @ContributesAndroidInjector(modules = [])
    internal abstract fun bindAuthPinCodeFragment(): SearchRepoFragment
}