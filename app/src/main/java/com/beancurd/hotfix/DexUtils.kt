package com.beancurd.hotfix

import com.beancurd.common.utils.LogE
import dalvik.system.BaseDexClassLoader


const val TAG = "DexUtils"

fun getDexElement(loader: BaseDexClassLoader): Array<*> {
    val pathList = getPathList(loader)
    LogE(TAG, "PathList is $pathList")
    val classDexElements = pathList::class.java.getDeclaredField("dexElements").apply {
        isAccessible = true
    }
    val dexElements = classDexElements.get(pathList)
    LogE(TAG, "dexElements is $dexElements")

    if (dexElements is Array<*>) {
        LogE(TAG, "dexElements size is ${dexElements.size}")
        return dexElements
    }

    throw java.lang.IllegalStateException("something error occurs ...")
}

fun setDexElement(loader: BaseDexClassLoader, arr: Array<*>):Boolean {
    val pathList = getPathList(loader)
    val classDexElements = pathList::class.java.getDeclaredField("dexElements").apply {
        isAccessible = true
    }
    classDexElements.set(pathList, arr)
    return true
}

fun getPathList(loader: BaseDexClassLoader): Any {
    val clzz = BaseDexClassLoader::class.java
    val clazzPathList = clzz.getDeclaredField("pathList").apply {
        isAccessible = true
    }
    return clazzPathList.get(loader)
}