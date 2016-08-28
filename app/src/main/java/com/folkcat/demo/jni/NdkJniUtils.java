package com.folkcat.demo.jni;

/**
 * Created by Tamas on 2016/8/28.
 */
public class NdkJniUtils {
    static {
        System.loadLibrary("firstJniLibName");	//defaultConfig.ndk.moduleName
    }
    public native String getCLanguageString();
}
