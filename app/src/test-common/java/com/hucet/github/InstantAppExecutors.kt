package com.hucet.github

import com.hucet.github.common.AppExecutors
import java.util.concurrent.Executor

class InstantAppExecutors : AppExecutors(instant, instant, instant) {
    companion object {
        val instant = Executor { it.run() }
    }
}