#include "ffmpeg.h"
#ifdef ANDROID
#include <jni.h>
#include <android/log.h>
#define LOGE(format,...) __android_log_print(ANDROID_LOG_ERROR,"myndk",format,##__VA_ARGS__)
#else
#define LOGE(format,...) printf("(>_<)"format "\n",##__VA_ARGS__)
#endif

static jclass jc;
static JNIEnv *genv;
static jobject jobj;
static int duration = -1;

JNIEXPORT jint JNICALL
Java_com_example_lq_myapplication_MainActivity_ffmpegRun(JNIEnv *env, jobject thiz,jobjectArray commands){
    jc = (*env)->GetObjectClass(env,thiz);
    genv = env;
    jobj = (*env)->NewGlobalRef(env, thiz);

 int argc = (*env)->GetArrayLength(env,commands);
 char *argv[argc];
 int i;
 for (i = 0; i < argc; i++) {
     jstring js = (jstring) (*env)->GetObjectArrayElement(env,commands, i);
     argv[i] = (char *) (*env)->GetStringUTFChars(env,js, 0);
 }
 LOGE("ff_run");
 int ret = ff_run(argc,argv);
 jc = NULL;
 jobj=NULL;
 genv = NULL;
 return ret;
}

int getDuration(char *ret) {
    int result = 0;
    char timeStr[10] = "Duration:";
    char *q = strstr(ret,timeStr);
    if (q != NULL) {
        char str[17] = {0};
        strncpy(str,q,16);
        LOGE("%s",str);

        int h = (str[5] - '0')* 10 + (str[6]-'0');
        int m = (str[8] - '0')* 10 + (str[9]-'0');
        int s = (str[11] - '0')* 10 + (str[12]-'0');
        int ms = (str[14] - '0') * 100 + (str[15] - '0') * 10;
         result = ms + (s + m * 60 + h * 60 * 60) * 1000;
         return result;
    } else {
        return -1;
    }
}

void callJavaMethod(char *ret) {
    if (duration == -1) {
        duration = getDuration(ret);
    }
    int result = 0;
    char timeStr[10] = "time=";
    char *q = strstr(ret,timeStr);
    if (q != NULL) {
        char str[17] = {0};
        strncpy(str,q,16);

        int h = (str[5] - '0')* 10 + (str[6]-'0');
        int m = (str[8] - '0')* 10 + (str[9]-'0');
        int s = (str[11] - '0')* 10 + (str[12]-'0');
        int ms = (str[14] - '0') * 100 + (str[15] - '0') * 10;
        result = ms + (s + m * 60 + h * 60 * 60) * 1000;
    } else {
        return;
    }
    jmethodID methodID = (*genv)->GetMethodID(genv, jc, "onProgress", "(II)V");
        //调用该方法
    (*genv)->CallVoidMethod(genv, jobj, methodID,result,duration);
}







