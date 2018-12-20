package by.overpass.conferclient.util

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

private val backgroundExecutor = AsyncTask.THREAD_POOL_EXECUTOR
private val uiHandler = Handler(Looper.getMainLooper())

fun runInBackground(task: () -> Unit) {
    backgroundExecutor.execute(task)
}

fun runOnUI(task: () -> Unit) {
    uiHandler.post(task)
}