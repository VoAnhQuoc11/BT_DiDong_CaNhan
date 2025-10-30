package com.example.demo

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.widget.Chronometer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    lateinit var stopwatch: Chronometer // Đồng hồ bấm giờ

    var running = false // Đồng hồ có đang chạy không?
    var offset: Long = 0 // Độ lệch cơ sở cho đồng hồ

    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stopwatch = findViewById<Chronometer>(R.id.stopwatch)

        // Khôi phục trạng thái trước đó
        if (savedInstanceState != null) {
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if (running) {
                stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                stopwatch.start()
            } else {
                setBaseTime()
            }
        }

        val showDialogButton = findViewById<Button>(R.id.showDialogButton)
        showDialogButton.setOnClickListener {

            val intent = Intent(this, DialogActivity::class.java)
            startActivity(intent)
        }

    val startButton = findViewById<Button>(R.id.start_button)
    startButton.setOnClickListener {
        if (!running) {
            setBaseTime()
            stopwatch.start()
            running = true
        }
    }
    val pauseButton = findViewById<Button>(R.id.pause_button)
    pauseButton.setOnClickListener {
        if (running) {
            saveOffset()
            stopwatch.stop()
            running = false
        }
    }
    val resetButton = findViewById<Button>(R.id.reset_button)
    resetButton.setOnClickListener {
        offset = 0
        setBaseTime()
    }

}
    fun setBaseTime() {
        stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - stopwatch.base
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong(OFFSET_KEY, offset)
        savedInstanceState.putBoolean(RUNNING_KEY, running)
        savedInstanceState.putLong(BASE_KEY, stopwatch.base)
        super.onSaveInstanceState(savedInstanceState)
    }
    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart Called")

        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setMessage("Hello")
        builder.setCancelable(true)
        builder.setPositiveButton("OK", null)
        val dialog = builder.create()
        dialog.show()
    }


    override fun onPause() {
        super.onPause()
        if (running) {
            saveOffset()
            stopwatch.stop()
        }
    }


    override fun onStop() {
        super.onStop()
        if (running) {
            saveOffset()
            stopwatch.stop()
        }
    }
    override fun onResume() {
        super.onResume()
        if (running) {
            setBaseTime()
            stopwatch.start()
        }
    }
    override fun onRestart() {
        super.onRestart()
        if (running) {
            offset = 0
            setBaseTime()
            stopwatch.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Called. ")
    }
}