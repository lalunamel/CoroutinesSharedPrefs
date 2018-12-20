package net.codysehl.coroutinessharedprefs

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferencesWrapper = SharedPreferencesWrapper(this)

        Log.e("CoroutineSharedPrefs", "Activity thread: ${Thread.currentThread()}")

        activity_main_button.setOnClickListener {
            Log.e("CoroutineSharedPrefs", "onClickListener thread: ${Thread.currentThread()}")
            GlobalScope.launch(Dispatchers.Main) {
                Log.e("CoroutineSharedPrefs", "coroutine start thread: ${Thread.currentThread()}")
                val value = sharedPreferencesWrapper.writeValue("hello world")
                activity_main_text.text = value
                Log.e("CoroutineSharedPrefs", "coroutine end thread: ${Thread.currentThread()}")
            }
        }
    }
}

class SharedPreferencesWrapper(context: Context) {
    val sharedPreferences = context.getSharedPreferences("sharedpreferences", Context.MODE_PRIVATE)

    @SuppressLint("ApplySharedPref")
    fun writeValue(value: String): String {
        Log.e("CoroutineSharedPrefs", "shared prefs start thread: ${Thread.currentThread()}")
        sharedPreferences.edit().apply {
            (0..50000).forEach {
                putString("key$it", value + it + Math.random())
            }
        }.commit()
        Log.e("CoroutineSharedPrefs", "shared prefs end thread: ${Thread.currentThread()}")
        return Math.random().toString()
    }
}