#include <jni.h>
#include <cstring>
#include <cstdio>
#include <string.h>

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wunused-parameter"

static const char apiUrl[] = "https://random-data-api.com/api/";

extern "C" jstring
Java_com_dienty_structure_common_NetworkModule_getBaseUrl(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(apiUrl);
}

#pragma clang diagnostic pop

