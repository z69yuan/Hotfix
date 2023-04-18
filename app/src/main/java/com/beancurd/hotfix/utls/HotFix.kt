package com.beancurd.hotfix.utls

import android.content.Context
import android.util.Log
import com.beancurd.common.utils.LogE
import com.beancurd.hotfix.getDexElement
import com.beancurd.hotfix.getNativeElement
import com.beancurd.hotfix.setDexElement
import dalvik.system.BaseDexClassLoader
import java.io.File

const val TAG = "HotFix"

fun installNewDex(context: Context,appClassLoader: ClassLoader) {

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
    val oldElements = getDexElement(appClassLoader as BaseDexClassLoader)
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
    val isSuccess = setDexElement(appClassLoader as BaseDexClassLoader, arr)
    LogE(TAG,"setDexElement isSuccess : $isSuccess")

    val recentElement = getDexElement(appClassLoader as BaseDexClassLoader)
    LogE(TAG,"recentElement is : ${recentElement.size}")

}


fun installNewSo(context: Context,appClassLoader: ClassLoader) {

    LogE(TAG, "*********************************")
    val file = File(context.cacheDir,"classes3.dex")
    if(!file.exists()){
        LogE(TAG, "$file does not exist ....")
        return
    }
    // 1. 加载插件包
    val loader = BaseDexClassLoader(file.absolutePath,null,context.cacheDir.absolutePath,null)
    val newElements = getNativeElement(loader)
    LogE(TAG,"newElements is : $newElements")
    var result = loader.findLibrary("crypt")
    LogE(TAG,"result : $result")

    var oldElements = getNativeElement(appClassLoader as BaseDexClassLoader)
    (oldElements as ArrayList<File>).apply {
        clear()
        add(File(context.cacheDir.absolutePath))
    }
    val oldElementsV2 = getNativeElement(appClassLoader as BaseDexClassLoader)
    Log.e("zfc","native equals ${oldElements === oldElementsV2}")

    result = loader.findLibrary("crypt")

    LogE(TAG,"resultV2 : $result")

    /**
     *
    package dalvik.system;

    public final class VMStack {
    public VMStack() {
    throw new RuntimeException("Stub!");
    }

    public static native ClassLoader getCallingClassLoader();
     */
    Class.forName("dalvik.system.VMStack").declaredMethods.forEach {
        if(it.name.equals("getCallingClassLoader")) {
            val callLoader = it.invoke(null)
            LogE(TAG, "callLoader is $callLoader")
        }
    }

}