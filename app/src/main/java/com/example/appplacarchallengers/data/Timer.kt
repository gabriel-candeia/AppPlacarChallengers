package ufc.smd.esqueleto_placar.data

import android.os.Handler
import android.os.Looper

class Timer(private val updateInterval: Long = 1000, private var elapsedTime: Long = 0, private val onTick: (Long) -> Unit) {
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var isRunning: Boolean = false
    private var runnable: Runnable

    init {
        runnable = object : Runnable {
            override fun run() {
                if (isRunning) {
                    elapsedTime += updateInterval / 1000
                    onTick(elapsedTime)
                    handler.postDelayed(this, updateInterval)
                }
            }
        }
    }

    fun toggle() : Boolean{
        if(isRunning) {
            pause()
            return false
        }
        else {
            start()
            return true
        }
    }

    fun start() {
        if (!isRunning) {
            isRunning = true
            handler.post(runnable)
        }
    }

    fun pause() {
        if (isRunning) {
            isRunning = false
            handler.removeCallbacks(runnable)
        }
    }

    fun reset(start: Long = 0) {
        pause()
        elapsedTime = start
        onTick(elapsedTime)
    }

    fun getElapsedTime(): Long {
        return elapsedTime
    }
}