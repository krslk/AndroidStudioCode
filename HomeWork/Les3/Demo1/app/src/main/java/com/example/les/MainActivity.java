package com.example.les;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.example.les.ex.DividingByZeroException;
import com.example.les.ex.OperandFormatException;
import com.example.les.utils.ExpressionCalculate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView inputTextView;
    private TextView outputTextView;
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
        findViewById(R.id.btn_mod).setEnabled(false);
        findViewById(R.id.btn_sign).setEnabled(false);
        inputTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    ExpressionCalculate expressionCalculate = new ExpressionCalculate(inputTextView.getText().toString());
                    outputTextView.setTextColor(Color.WHITE);
                    result = expressionCalculate.getResult();
                    outputTextView.setTextSize(55);
                    outputTextView.setText(result);
                } catch (OperandFormatException e) {
                    inputTextView.setText(removeTail(inputTextView.getText().toString()));
                } catch (DividingByZeroException e) {
                    result = "错误";
                    outputTextView.setText(result);
                    outputTextView.setTextSize(55);
                    outputTextView.setTextColor(Color.RED);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        String inputText = ((TextView) v).getText().toString();
        if (v.getId() == R.id.btn_AC) {
            inputTextView.setText("");
            outputTextView.setText("0");
        } else if (v.getId() == R.id.btn_back) {
            inputTextView.setText(removeTail(inputTextView.getText().toString()));
        } else if (v.getId() == R.id.btn_zero) {
            String str = inputTextView.getText().toString();
            if (appendZeroCheck(str)) {
                inputTextView.append(inputText);
            }
        } else if (v.getId() == R.id.btn_equal) {
            outputTextView.setTextSize(75);
            outputTextView.setText(result);
        } else if (v.getId() == R.id.btn_mod) {

        } else {
            inputTextView.append(inputText);
        }
    }

    protected boolean appendZeroCheck(String str) {
        if (str.isEmpty() || isSign(str.charAt(str.length() - 1))) {
            return true;
        }
        for (int i = str.length() - 1; i >= 0 && !isSign(str.charAt(i)); i--) {
            if (str.charAt(i) == '.') {
                return true;
            }
        }
        return false;
    }

    protected boolean isSign(char c) {
        if (c == '+' || c == '-' || c == '×' || c == '÷') return true;
        return false;
    }

    protected String removeTail(String s) {
        if (!s.isEmpty()) {
            return s.substring(0, s.length() - 1);
        }
        return "";
    }
}