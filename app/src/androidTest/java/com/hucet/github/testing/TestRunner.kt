package com.hucet.github.testing

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.hucet.github.TestApplication

/**
 * Custom runner to disable dependency injection.
 */
class TestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}