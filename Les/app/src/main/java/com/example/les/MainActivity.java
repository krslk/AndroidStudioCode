package com.example.les;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.nio.Buffer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView inputTextView;
    private TextView outputTextView;
    private StringBuffer firstOperand = new StringBuffer();
    private StringBuffer lastOperand = new StringBuffer();
    private String operator = "";
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputTextView = findViewById(R.id.tv_inputView);
        outputTextView = findViewById(R.id.tv_resultView);
        findViewById(R.id.btn_AC).setOnClickListener(this);
        findViewById(R.id.btn_mod).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_div).setOnClickListener(this);
        findViewById(R.id.btn_mul).setOnClickListener(this);
        findViewById(R.id.btn_sub).setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_equal).setOnClickListener(this);
        findViewById(R.id.btn_point).setOnClickListener(this);
        findViewById(R.id.btn_sign).setOnClickListener(this);
        findViewById(R.id.btn_zero).setOnClickListener(this);
        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_four).setOnClickListener(this);
        findViewById(R.id.btn_five).setOnClickListener(this);
        findViewById(R.id.btn_six).setOnClickListener(this);
        findViewById(R.id.btn_seven).setOnClickListener(this);
        findViewById(R.id.btn_eight).setOnClickListener(this);
        findViewById(R.id.btn_nine).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String inputText = ((TextView) v).getText().toString();
        if (v.getId() == R.id.btn_AC) {
            inputTextView.setText("");
            outputTextView.setText("0");
        } else if (v.getId() == R.id.btn_back) {
            String str = inputTextView.getText().toString();
            if (!str.isEmpty()) {
                inputTextView.setText(str.substring(0, str.length() - 1));
            }
        } else if (v.getId() == R.id.btn_mod || v.getId() == R.id.btn_sign) {

        } else if (v.getId() == R.id.btn_div || v.getId() == R.id.btn_mul || v.getId() == R.id.btn_sub || v.getId() == R.id.btn_add) {

        } else if (v.getId() == R.id.btn_point) {

        } else if (v.getId() == R.id.btn_equal) {
        } else if (v.getId() == R.id.btn_zero) {

        } else {
            inputTextView.append(inputText);
            if (operator == null) {
                firstOperand.append(inputText);
            } else {
                lastOperand.append(inputText);
            }
        }
    }

    private String removeFromTail(String str) {
        if (str != null && !str.equals("")) {
            return str.substring(0, str.length() - 1);
        }
        return "";
    }

}