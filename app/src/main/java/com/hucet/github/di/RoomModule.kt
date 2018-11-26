package com.hucet.github.di

import android.app.Application
import com.hucet.github.db.GithubDabase
import com.hucet.github.db.RepoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Singleton
    @Provides
    internal fun providesRoomDatabase(application: Application): GithubDabase = GithubDabase.getInstance(application)

    @Singleton
    @Provides
    internal fun providesColorThemeDao(dataBase: GithubDabase): RepoDao = dataBase.repoDao()
}