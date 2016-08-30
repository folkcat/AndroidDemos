package com.folkcat.demo.jni;

/**
 * Created by Tamas on 2016/8/28.
 */
public class NdkJniUtils {
    static {
        System.loadLibrary("firstJniLibName");	//defaultConfig.ndk.moduleName
    }
    native double f(int i, String s);
    public native String getCLanguageString();
}
