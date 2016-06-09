package com.folkcat.demo.gles;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.folkcat.demo.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Tamas on 2016/6/10.
 */
public class GLActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gles);
        GLSurfaceView glSurfaceView=(GLSurfaceView)findViewById(R.id.sv_gl);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(new MyRenderer());
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
    static class MyRenderer implements GLSurfaceView.Renderer{
        MyRenderer(){

        }
        @Override
        public void onSurfaceCreated(GL10 unused,EGLConfig config){

        }
        @Override
        public void onSurfaceChanged(GL10 unused,int width,int height){
            //TODO 加载GLSL程序等绘制初始化工作
        }
        @Override
        public void onDrawFrame(GL10 unused){
            //TODO  具体绘制工作
        }
    }
}
