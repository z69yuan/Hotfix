package com.beancurd.hotfix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.beancurd.crypt.NativeLib

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_onclick).setOnClickListener {
            showToast()
        }
    }

    private fun showToast() {
        Toast.makeText(this,NativeLib().stringFromJNI(),Toast.LENGTH_SHORT).show()
    }
}