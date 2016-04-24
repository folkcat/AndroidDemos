package com.folkcat.demo.classloader;

import android.util.Log;

/**
 * Created by Tamas on 2016/4/24.
 */
public class InterfaceTest implements MainInterface {
    private static final String TAG="InterfaceTest";
    @Override
    public String sayHello(){
        return "hello world aaa ";
    }
}
