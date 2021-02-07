package com.udacity.asteroidradar

//import com.udacity.asteroidradar.MainActivity.Companion.WORK_NAME
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //delayedInit()
        setContentView(R.layout.activity_main)
    }
}
