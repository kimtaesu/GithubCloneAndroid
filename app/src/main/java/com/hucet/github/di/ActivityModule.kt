package com.hucet.github.di

import com.hucet.github.MainActivity
import com.hucet.github.di.fragment.SearchRepoFragmentModule
import com.hucet.github.di.scopes.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [SearchRepoFragmentModule::class])
    internal abstract fun bindMainActivity(): MainActivity
}