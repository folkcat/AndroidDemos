LOCAL_PATH := $(call my-dir)
    include $(CLEAR_VARS)
    #编译生成的文件的类库叫什么名字
    LOCAL_MODULE    := firstJniLibName
    #要编译的c文件
    LOCAL_SRC_FILES := test_jni.c
    include $(BUILD_SHARED_LIBRARY)