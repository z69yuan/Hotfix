package com.beancurd.hotfix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tv_onclick).setOnClickListener {
            showToast();
        }
    }

    private fun showToast() {
        val i = 5/0
        Toast.makeText(this,"hello world",0).show()
    }
}