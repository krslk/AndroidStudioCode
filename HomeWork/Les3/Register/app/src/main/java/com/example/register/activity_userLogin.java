package com.example.register;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.register.Databases.UserDBHelper;
import com.example.register.entity.User;
import com.example.register.exception.PasswordIsNullException;
import com.example.register.exception.UsernameIsNullException;

import java.io.Serializable;

public class activity_userLogin extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup loginMeans;
    private EditText username;
    private EditText password;
    private EditText phone;
    private EditText code;
    private Button getCode;
    private Button login;
    private TextView tvUsername;
    private TextView tvPassword;
    private LinearLayout tvGroupGetCode;
    private TextView tvPhone;
    private CheckBox saveUsernameAndPassword;
    SQLiteDatabase sqLiteDatabase;
    private UserDBHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);
        loginMeans = findViewById(R.id.rg_login_means);
        username = findViewById(R.id.et_login_username);
        password = findViewById(R.id.et_login_password);
        phone = findViewById(R.id.et_login_phone);
        code = findViewById(R.id.et_login_code);
        getCode = findViewById(R.id.btn_login_code);
        login = findViewById(R.id.btn_login);
        tvUsername = findViewById(R.id.tv_login_username);
        tvPassword = findViewById(R.id.tv_login_password);
        tvPhone = findViewById(R.id.tv_login_phone);
        tvGroupGetCode = (LinearLayout) findViewById(R.id.tv_login_group_getCode);
        saveUsernameAndPassword = findViewById(R.id.cb_save_username_password);
        String myDatabaseNames = getFilesDir() + "./test.db";
        sqLiteDatabase = openOrCreateDatabase(myDatabaseNames, Context.MODE_PRIVATE, null);
        loginMeans.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_login_by_username) { //用户名密码登录
                    tvUsername.setVisibility(View.VISIBLE);
                    username.setVisibility(View.VISIBLE);
                    password.setVisibility(View.VISIBLE);
                    tvPassword.setVisibility(View.VISIBLE);
                    saveUsernameAndPassword.setVisibility(View.VISIBLE);
                    tvPhone.setVisibility(View.GONE);
                    phone.setVisibility(View.GONE);
                    tvGroupGetCode.setVisibility(View.GONE);
                } else {//验证码登录
                    tvUsername.setVisibility(View.GONE);
                    username.setVisibility(View.GONE);
                    password.setVisibility(View.GONE);
                    tvPassword.setVisibility(View.GONE);
                    tvPhone.setVisibility(View.VISIBLE);
                    phone.setVisibility(View.VISIBLE);
                    tvGroupGetCode.setVisibility(View.VISIBLE);
                    saveUsernameAndPassword.setVisibility(View.GONE);
                }
            }
        });
        loginMeans.check(R.id.rb_login_by_username);//设置默认的登录方式
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = null;
                try {
                    user = myHelper.selectUserByUsernameAndPassword(getUseFromActivity());
                } catch (PasswordIsNullException | UsernameIsNullException e) {
                    Toast.makeText(activity_userLogin.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (user != null && user.getId() != null) {
                    Toast.makeText(activity_userLogin.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity_userLogin.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(activity_userLogin.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        myHelper = UserDBHelper.getInstance(this);
        myHelper.openReadLink();
        myHelper.openWriteLink();
        User user = myHelper.selectRememberInfo();
        if (user.getUsername() != null && user.getPassword() != null) {
            username.setText(user.getUsername());
            password.setText(user.getPassword());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (saveUsernameAndPassword.isChecked()) {
            User user = null;
            try {
                user = getUseFromActivity();
            } catch (UsernameIsNullException | PasswordIsNullException e) {
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Log.w("Test", user.toString());
            if (user != null) {
                myHelper.insertRememberInfo(user);
            }
        }
        myHelper.closeLink();
    }

    public User getUseFromActivity() { //获取登录信息并封装称User对象
        User user = new User();
        if (loginMeans.getCheckedRadioButtonId() == R.id.rb_login_by_username) {//使用用户名密码登录
            if (username.getText().toString().isEmpty()) { //用户名不为空
                throw new UsernameIsNullException();
            }
            if (password.getText().toString().isEmpty()) { //密码不为空
                throw new PasswordIsNullException();
            }
            user.setUsername(username.getText().toString());
            user.setPassword(password.getText().toString());
            user.setStatus(1);
            user.setCreateTime(""); //该信息后续完善
            user.setModifyTime("");
        } else {//使用手机号登录(此功能后续完善)

        }
        return user;
    }

    @Override
    public void onClick(View v) {

    }
}