#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_beancurd_crypt_NativeLib_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++V2";
    return env->NewStringUTF(hello.c_str());
}