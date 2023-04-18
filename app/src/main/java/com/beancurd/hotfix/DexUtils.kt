package com.beancurd.hotfix

import com.beancurd.common.utils.LogE
import dalvik.system.BaseDexClassLoader
import java.io.File


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


/**
 *  private final File[] nativeLibraryDirectories;
 */
fun getNativeElement(loader: BaseDexClassLoader): ArrayList<*> {
    val pathList = getPathList(loader)
    LogE(TAG, "PathList is $pathList")
    val nativeLibraryDirectories = pathList::class.java.getDeclaredField("nativeLibraryDirectories").apply {
        isAccessible = true
    }
    val fileList = nativeLibraryDirectories.get(pathList)
    LogE(TAG, "fileList size is ${fileList.javaClass}")
    if (fileList is ArrayList<*>) {
        LogE(TAG, "fileList size is ${fileList}")
        return fileList
    }

    throw java.lang.IllegalStateException("something error occurs ...")
}

//fun setDexNativeLib(loader: BaseDexClassLoader, arr: Array<*>):Boolean {
//    val pathList = getPathList(loader)
//    val classDexElements = pathList::class.java.getDeclaredField("dexElements").apply {
//        isAccessible = true
//    }
//    classDexElements.set(pathList, arr)
//    return true
//}

