package com.hucet.github.di

import dagger.Module

@Module(includes = [NetworkModule::class, ViewModelModule::class, ActivityModule::class])
class AppModule {}