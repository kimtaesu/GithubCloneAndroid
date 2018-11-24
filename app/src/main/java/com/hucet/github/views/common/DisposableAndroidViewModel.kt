package com.hucet.github.views.common

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class DisposableAndroidViewModel(app: Application) : AndroidViewModel(app) {
    protected val dispose = CompositeDisposable()

    fun addToDispose(d: Disposable) {
        dispose.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        dispose.dispose()
    }
}