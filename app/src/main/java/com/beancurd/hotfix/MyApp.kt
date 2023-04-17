package com.beancurd.hotfix

import android.app.Application
import android.content.Context
import com.beancurd.common.utils.LogE
import dalvik.system.BaseDexClassLoader
import java.io.File
import kotlin.reflect.javaType

class MyApp : Application() {

    private val TAG = "MyApplication"

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        base?:return
        LogE(TAG, "*********************************")
        // 1. 加载插件包
//        val file = File(base.cacheDir,"appv1.apk")
        val file = File(base.cacheDir,"classes3.dex")
        if(!file.exists()){
            LogE(TAG, "$file does not exist ....")
            return
        }

        // String dexPath, File optimizedDirectory, String librarySearchPath, ClassLoader parent
        val loader = BaseDexClassLoader(file.absolutePath,null,null,null)

        val clzz = BaseDexClassLoader::class.java
        val clazzPathList = clzz.getDeclaredField("pathList").apply {
            isAccessible = true
        }
        val pathList = clazzPathList.get(loader)
        LogE(TAG,"PathList is $pathList")
        val classDexElements = pathList::class.java.getDeclaredField("dexElements").apply {
            isAccessible = true
        }
        val dexElements = classDexElements.get(pathList)
        LogE(TAG,"dexElements is $dexElements")

        if(dexElements is Array<*>) {
            LogE(TAG,"dexElements size is ${dexElements.size}")
        }



//        val clazz = loader.loadClass("com.beancurd.hotfix.MainActivity")
//        LogE(TAG, "clazz is $clazz")
        // 2. 全局替换类
    }
}