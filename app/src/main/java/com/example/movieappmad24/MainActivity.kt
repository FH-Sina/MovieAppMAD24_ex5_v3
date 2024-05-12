package com.example.movieappmad24

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.movieappmad24.navigation.Navigation
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD24Theme {
                Navigation()
            }
        }
        logEvent("onCreate")
    }

    override fun onStart() {
        super.onStart()
        logEvent("onStart")
    }


    override fun onResume() {
        super.onResume()
        logEvent("onResume")
    }


    override fun onStop() {
        super.onStop()
        logEvent("onStop")
    }


    override fun onPause() {
        super.onPause()
        logEvent("onPause")
    }


    override fun onRestart() {
        super.onRestart()
        logEvent("onRestart")
    }


    override fun onDestroy() {
        super.onDestroy()
        logEvent("onDestroy")
    }


    private fun logEvent(lifecycleEvent: String) {
        Log.i("MainActivity", "$lifecycleEvent called.")
    }
}
