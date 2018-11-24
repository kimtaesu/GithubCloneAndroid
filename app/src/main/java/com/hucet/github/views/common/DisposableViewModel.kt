package com.hucet.github.views.common

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class DisposableViewModel : ViewModel() {
    protected val dispose = CompositeDisposable()

    fun addToDispose(d: Disposable) {
        dispose.add(d)
    }
    override fun onCleared() {
        super.onCleared()
        dispose.dispose()
    }
}