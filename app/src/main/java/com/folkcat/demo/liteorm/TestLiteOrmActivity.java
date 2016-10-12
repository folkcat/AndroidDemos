package com.folkcat.demo.liteorm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.folkcat.demo.R;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;

import java.util.ArrayList;

public class TestLiteOrmActivity extends AppCompatActivity implements View.OnClickListener {

    private DataBase mainDB;
    private static final String TAG="TestLiteOrmActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_lite_orm);
        findViewById(R.id.btn_create).setOnClickListener(this);
        findViewById(R.id.btn_insert).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_query_all).setOnClickListener(this);
        findViewById(R.id.btn_query_single).setOnClickListener(this);

    }

    /**
     * 按钮事件的监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create:
                // 创建数据库
                if (mainDB == null) {
                    // 创建数据库,传入当前上下文对象和数据库名称
                    mainDB = LiteOrm.newSingleInstance(TestLiteOrmActivity.this,
                            "test_lite_orm.db");
                    Log.i(TAG,"创建数据库成功");
                }

                break;
            case R.id.btn_insert:
                // 新增数据
                for(int i=20;i<25;i++){
                    User user = new User("User "+i, 1, "13859900"+i,
                            "email@email.com");
                    long num=mainDB.save(user);
                    Log.i(TAG,"save num:"+num);
                }



                break;
            case R.id.btn_delete:
                // 删除指定数据
                // 删除第2到第5条 ,换句话说就是删除第二、三、四、五条数据
                // 最后一个参数可为null，默认按ID升序排列
                mainDB.delete(User.class, 2, 5, "_id");
                break;
            case R.id.btn_update:
                // 修改数据

                // 查询得到全部数据
                ArrayList<User> userDatas = mainDB.query(User.class);
                // 设置第一个数据的Name
                userDatas.get(3).setUserName("董海峰");
                // 修改数据
                mainDB.update(userDatas.get(3));
                break;
            case R.id.btn_query_single:
                // 查询一条数据
                //指定查询ID和查询对象
                User u2=mainDB.queryById(11,User.class);
                Log.i("data",u2.getUserName());
                break;
            case R.id.btn_query_all:
                // 查询全部数据
                ArrayList<User> userList = mainDB.query(User.class);
                //得到数据库中有多少条数据
                Log.i("data", ""+userList.size());
                break;
            default:
                break;
        }
    }



}
