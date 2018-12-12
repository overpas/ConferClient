package by.overpass.conferclient.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

private val cpuCount = Runtime.getRuntime().availableProcessors()
private val desiredThreadNumber = (2 + cpuCount / 2) / 2
private val backgroundExecutor = Executors.newFixedThreadPool(if (desiredThreadNumber > 1) desiredThreadNumber else 2)
private val uiHandler = Handler(Looper.getMainLooper())

fun runInBackground(task: () -> Unit) {
    backgroundExecutor.execute(task)
}

fun runOnUI(task: () -> Unit) {
    uiHandler.post(task)
}