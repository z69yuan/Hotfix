package com.beancurd.hotfix

import android.app.Application
import android.content.Context
import com.beancurd.hotfix.utls.installNewSo

class MyApp : Application() {

    private val TAG = "MyApplication"

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        base?:return

        // installNewDex(base, classLoader)

        // 修复动态库
        installNewSo(base, classLoader)
    }

}