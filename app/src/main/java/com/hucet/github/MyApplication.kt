package com.hucet.github

import android.app.Activity
import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.hucet.github.debug.OptionalTree
import com.hucet.github.di.AppInjector
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import com.squareup.leakcanary.LeakCanary
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import javax.inject.Inject

open class MyApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        initDagger()
        initFabric()
        initPreferenceHolder()
        initLeakCanary()
        initTimber()
    }

    open fun initDagger() {
        AppInjector.init(this)
    }

    open fun initFabric() {
        // Initializes Fabric for builds that don't use the debug build type.
        val crashlyticsKit = Crashlytics.Builder()
            .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
            .build()

        Fabric.with(this, crashlyticsKit)
    }

    open fun initPreferenceHolder() {
        PreferenceHolder.setContext(this)
        if (BuildConfig.DEBUG) {
            PreferenceHolder.testingMode = true
        }
    }

    open fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(OptionalTree(threadName = true))
        }
    }

    open fun initLeakCanary() {
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return
            }
            LeakCanary.install(this)
        }
    }
}