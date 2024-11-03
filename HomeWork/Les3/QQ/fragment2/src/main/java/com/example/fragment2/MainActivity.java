package com.example.fragment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 1. 获取FragmentManagement，通过getSupperFragmentManagement()
 * 2. 开启事务transaction，一般调用getSupperFragmentManagement的beginTransaction()方法
 * 3. 使用transaction进行fragment进行替换
 * 4. 提交事务
 * 5. 数据传输使用transaction的setArguments()方法绑定Bundle数据
 * 6. 在fragment中使用getArguments()获取Bundle数据
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button changeBtn = findViewById(R.id.btn_change);
        changeBtn.setOnClickListener(this);
        Button replaceBtn = findViewById(R.id.btn_replace);
        replaceBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_change) {
            Bundle bundle = new Bundle();
            bundle.putString("username", "OuJiangLan");
            bundle.putString("password", "123456");
            BlankFragment1 bf = new BlankFragment1();
            bf.setArguments(bundle);//绑定数据
            replaceFragment(bf);
        } else if (v.getId() == R.id.btn_replace) {
            replaceFragment(new BlankFragment2());
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment1, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}