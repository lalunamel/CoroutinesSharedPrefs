package net.codysehl.coroutinessharedprefs

import android.app.Application
import android.os.StrictMode



class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyFlashScreen()
                .build()
        )
    }
}