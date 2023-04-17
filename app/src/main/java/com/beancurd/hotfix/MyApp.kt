package com.beancurd.hotfix

import android.app.Application
import android.content.Context
import com.beancurd.common.utils.LogE
import dalvik.system.BaseDexClassLoader
import java.io.File

class MyApp : Application() {

    private val TAG = "MyApplication"

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        base?:return

        installNewDex(base)
    }

    private fun installNewDex(context: Context) {

        LogE(TAG, "*********************************")
        // 1. 加载插件包
//        val file = File(base.cacheDir,"appv1.apk")
        val file = File(context.cacheDir,"classes3.dex")
        if(!file.exists()){
            LogE(TAG, "$file does not exist ....")
            return
        }

        // String dexPath, File optimizedDirectory, String librarySearchPath, ClassLoader parent
        val loader = BaseDexClassLoader(file.absolutePath,null,null,null)
        val newElements = getDexElement(loader)
        LogE(TAG,"newElements is : ${newElements.size}")
        val oldElements = getDexElement(classLoader as BaseDexClassLoader)
        LogE(TAG,"oldElements is : ${oldElements.size}")
        // 2. 全局替换类
        val arr = java.lang.reflect.Array.newInstance(
            oldElements[0]!!.javaClass,
            newElements.size + oldElements.size
        ) as Array<Any>

        val len = arr.size
        val newLen = newElements.size
        for (i in 0 until len) {
            arr.set(i, if(i < newLen) {
                newElements[i]!!
            } else {
                oldElements[i-newLen]!!
            })
        }

//        LogE(TAG,"setDexElement isSuccess : $arr")
        val isSuccess = setDexElement(classLoader as BaseDexClassLoader,arr)
        LogE(TAG,"setDexElement isSuccess : $isSuccess")

        val recentElement = getDexElement(classLoader as BaseDexClassLoader)
        LogE(TAG,"recentElement is : ${recentElement.size}")

    }
}