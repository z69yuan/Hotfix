package com.beancurd.hotfix

import android.app.Application
import android.content.Context
import com.beancurd.hotfix.utls.installNewDex

class MyApp : Application() {

    private val TAG = "MyApplication"

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        base?:return

        installNewDex(base, classLoader)
    }

}