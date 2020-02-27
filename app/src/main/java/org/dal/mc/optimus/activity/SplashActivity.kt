package org.dal.mc.optimus.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import org.dal.mc.optimus.R
import org.dal.mc.optimus.util.navigateTo

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //It will remove action bar for this activity only.
        supportActionBar?.hide()
        scheduleSplashScreen()
    }

    private fun scheduleSplashScreen() {
        Handler().postDelayed({
            //Finish splash activity so that it can't be returned to.
            this.finish()
            navigateTo(this,MainActivity::class.java)
        }, getSplashScreenTime())
    }
    private fun getSplashScreenTime(): Long = 2000
}
