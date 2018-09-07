LOCAL_PATH :=$(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := avcodec
LOCAL_SRC_FILES := libavcodec-57.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := avfilter
LOCAL_SRC_FILES := libavfilter-6.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := avformat
LOCAL_SRC_FILES := libavformat-57.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := avutil
LOCAL_SRC_FILES := libavutil-55.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := swresample
LOCAL_SRC_FILES := libswresample-2.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := swscale
LOCAL_SRC_FILES := libswscale-4.so
include $(PREBUILT_SHARED_LIBRARY)

#Program
include $(CLEAR_VARS)
LOCAL_MODULE :=sffhelloworld
LOCAL_SRC_FILES := simplest_ffmpeg_helloworld.c
LOCAL_C_INCLUDES += $(LOCAL_PATH)/include
LOCAL_LDLIBS := -llog -lz
LOCAL_SHARED_LIBRARIES := avcodec avfilter avformat avutil swresample swscale
include $(BUILD_SHARED_LIBRARY)


include $(CLEAR_VARS)
LOCAL_MODULE := ffmpegrun
LOCAL_SRC_FILES := cmdutils.c ffmpeg.c ffmpeg_filter.c ffmpeg_opt.c ffmpeg_run_impl.c

LOCAL_C_INCLUDES :=/Users/LesPark-sh/Downloads/ffmpeg-3.2.12
LOCAL_LDLIBS := -llog -lz
LOCAL_SHARED_LIBRARIES :=libavcodec libavfilter libavformat libavutil libswresample libswscale
include $(BUILD_SHARED_LIBRARY)