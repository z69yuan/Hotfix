package com.beancurd.common.utils


const val TAG = "XClassLoader"

fun printCurrentLoader() {
    Class.forName("dalvik.system.VMStack").declaredMethods.forEach {
        if(it.name.equals("getCallingClassLoader")) {
            val callLoader = it.invoke(null)
            LogE(TAG, "callLoader is $callLoader")
        }
    }
}