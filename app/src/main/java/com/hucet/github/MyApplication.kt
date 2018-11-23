package com.hucet.github

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import io.fabric.sdk.android.Fabric

open class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initFabric()
    }

    open fun initFabric() {
        // Initializes Fabric for builds that don't use the debug build type.
        val crashlyticsKit = Crashlytics.Builder()
            .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
            .build()

        Fabric.with(this, crashlyticsKit)
    }
}