package com.folkcat.demo.classloader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.folkcat.demo.R;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.IllegalFormatCodePointException;

import dalvik.system.DexClassLoader;

/**
 * Created by Tamas on 2016/4/24.
 */
public class TestClassLoaderActivity extends Activity {
    public static final String TAG="TestClassLoaderActivity";
    private TextView mTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_classloader);
        mTv=(TextView)findViewById(R.id.tv_test_classloader);

        localTest(getApplicationContext());

        Log.i(TAG, "SD卡路径"+Environment.getExternalStorageDirectory().getPath());
    }

    private void localTest(Context context) {
        try {
            File sourceFile = new File(
                    Environment.getExternalStorageDirectory() + File.separator
                            + "output.jar");// 导出的jar的存储位置
            if(sourceFile.exists()){
                Log.i(TAG,"文件存在哦");
            }
            File file = context.getDir("osdk", 0);// dex临时存储路径
            DexClassLoader classLoader = new DexClassLoader(
                    sourceFile.getAbsolutePath(), file.getAbsolutePath(), null,
                    context.getClassLoader());

            Class<?> libProviderClazz = classLoader
                    .loadClass("com.folkcat.demo.classloader.InterfaceTest");

            MainInterface mMainInterface = (MainInterface) libProviderClazz
                    .newInstance();// 接口
            String str = mMainInterface.sayHello();// 获取jar包提供的数据
            mTv.setText(str);
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.i(TAG,"出错了");
            e.printStackTrace();
        }
    }

    private void netTest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String rootUrl = "http://blogstatic.folkcat.com";
                NetWorkClassLoader networkClassLoader = new NetWorkClassLoader(rootUrl,"/class_Foo8.class","me.yuefa.yuefa.net.Foo");
                Class clazz = null;
                try {
                    clazz = networkClassLoader.loadClass(" ");
                    Constructor constructor=clazz.getDeclaredConstructor(new Class[]{String.class});
                    constructor.setAccessible(true);
                    Object obj = constructor.newInstance(new Object[]{"FFFFprefix"});
                    Method method = clazz.getMethod("bar",String.class);
                    method.invoke(obj,"Mary");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e){
                    e.printStackTrace();
                } catch (IllegalAccessException e){
                    e.printStackTrace();
                } catch (InvocationTargetException e){
                    e.printStackTrace();
                } catch (InstantiationException e){
                    e.printStackTrace();
                }
                //Object obj = clazz.newInstance();


            }
        }).start();
    }
}

