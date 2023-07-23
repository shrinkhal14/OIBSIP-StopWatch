package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var stopwatchTextView: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button

    private var handler: Handler = Handler()
    private var isRunning: Boolean = false
    private var startTime: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stopwatchTextView = findViewById(R.id.stopwatchTextView)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)

        startButton.setOnClickListener { startStopwatch() }
        stopButton.setOnClickListener { stopStopwatch() }
    }

    private fun startStopwatch() {
        if (!isRunning) {
            isRunning = true
            startButton.isEnabled = false
            stopButton.isEnabled = true
            startTime = SystemClock.elapsedRealtime()

            handler.postDelayed(updateTimer, 0)
        }
    }

    private fun stopStopwatch() {
        if (isRunning) {
            isRunning = false
            startButton.isEnabled = true
            stopButton.isEnabled = false

            handler.removeCallbacks(updateTimer)
        }
    }

    private val updateTimer: Runnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                val currentTime = SystemClock.elapsedRealtime() - startTime
                val hours = currentTime / 3600000
                val minutes = (currentTime - hours * 3600000) / 60000
                val seconds = (currentTime - hours * 3600000 - minutes * 60000) / 1000

                val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                stopwatchTextView.text = formattedTime

                handler.postDelayed(this, 1000)
            }
        }
    }
}
