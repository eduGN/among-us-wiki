package com.example.amonguswiki.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import com.example.amonguswiki.R
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 800 //3 seconds
    private var mDelayHandler: Handler? = null
    private var progressBarStatus = 0
    private lateinit var auth: FirebaseAuth


    var dummy:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Navigate with delay
        mDelayHandler= Handler()
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    private fun launchHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        this.finish()
        mDelayHandler!!.removeCallbacks(mRunnable)
    }

    private fun launchMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        this.finish()
        mDelayHandler!!.removeCallbacks(mRunnable)
    }

    private val mRunnable: Runnable = Runnable(Thread(Runnable {

        val mProgressBar=findViewById<ProgressBar>(R.id.splash_screen_progress_bar)
        while (progressBarStatus < 300) {
            // performing some dummy operation
            try {
                dummy = dummy + 15
                Thread.sleep(300)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            // tracking progress
            progressBarStatus = dummy
            // Updating the progress bar
            mProgressBar.progress = progressBarStatus
        }
        //splash_screen_progress_bar.setProgress(10)


        //val currentUser = auth.currentUser
        //if (currentUser != null) {
            launchHomeActivity()
        //} else {
            launchMainActivity()
        //}
    })::start)
    override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }



}