#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_daniel_banklist_di_ConstantModule_getAppToken(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env,
                                "wnV24w-O8SJiHqk2DYzynz");
}

JNIEXPORT jstring JNICALL
Java_com_daniel_banklist_di_ConstantModule_getAppClientSecret(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "fq7emqsxGUdXvZ6ck2mcH6Tvf-GbUgZZlcB1UMKn7wb99ny");
}

JNIEXPORT jstring JNICALL
Java_com_daniel_banklist_di_ConstantModule_getBaseStagingURL(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "https://dev.meemee.online/");
}


JNIEXPORT jstring JNICALL
Java_com_daniel_banklist_di_ConstantModule_getBaseProductionURL(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "https://dev.meemee.online/");
}