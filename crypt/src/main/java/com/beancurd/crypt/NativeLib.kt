package com.beancurd.crypt

import android.util.Log

class NativeLib {

    var loader:ClassLoader? = null

    /**
     * A native method that is implemented by the 'crypt' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'crypt' library on application startup.
        init {
            Log.e("zfc","jni .....")
            // void load(String absolutePath, ClassLoader loader)
            System.loadLibrary("crypt")
            // Runtime.getRuntime().loadLibrary("crypt")
            // System.load("/data/user/0/com.beancurd.hotfix/cache/libcrypt.so")
        }
    }
}