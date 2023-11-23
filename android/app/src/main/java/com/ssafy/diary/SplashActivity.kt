package com.ssafy.diary

import android.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.diary.databinding.ActivityMainBinding
import com.ssafy.diary.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }
    var handler: Handler = Handler()
    var runnable = Runnable {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 상태바 삭제
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(binding.root)
        handler.postDelayed(runnable, 1000)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}