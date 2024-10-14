package com.example.les.utils;

import com.example.les.ex.DividingByZeroException;
import com.example.les.ex.OperandFormatException;

import java.util.Stack;

public class ExpressionCalculate {
    protected String expression;//待求值表达式
    protected Stack<Character> operateSigns;//存放操作符号栈
    protected Stack<Double> operand;//存放操作数栈
    protected String result;//存放计算结果

    public ExpressionCalculate(String expression) throws OperandFormatException {
        this.expression = expression;
        operand = new Stack<>();
        operateSigns = new Stack<>();
        getStacksFromExpression();
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Stack<Character> getOperateSigns() {
        return operateSigns;
    }

    public void setOperateSigns(Stack<Character> operateSigns) {
        this.operateSigns = operateSigns;
    }

    public Stack<Double> getOperand() {
        return operand;
    }

    public void setOperand(Stack<Double> operand) {
        this.operand = operand;
    }

    public String getResult() throws DividingByZeroException {
        this.result = String.valueOf(calculateExpression(operateSigns, operand));
        return result;
    }

    protected void getStacksFromExpression() throws OperandFormatException {
        if (expression.isEmpty()) {
            return;
        }
        StringBuffer string = new StringBuffer(this.expression);
        if (isSign(string.charAt(string.length() - 1))) {
            expression = expression.substring(0, expression.length() - 1);
            string.deleteCharAt(string.length() - 1);
        }
        for (int i = string.length() - 1; i >= 0; i--) {
            if (isSign(string.charAt(i)) == true) {
                try {
                    operand.push(Double.parseDouble(string.substring(i + 1, string.length())));
                    string.delete(i + 1, string.length());
                } catch (NumberFormatException e) {
                    throw new OperandFormatException(e);
                }
                operateSigns.push(string.charAt(i));
                string.deleteCharAt(i);
            }
        }
        try {
            operand.push(Double.parseDouble(String.valueOf(string)));
        } catch (NumberFormatException e) {
            throw new OperandFormatException(e);
        }
    }

    protected Double calculateExpression(Stack<Character> operateSigns, Stack<Double> operand) throws DividingByZeroException {
        Double ans = 0D;
        while (!operateSigns.isEmpty()) {
            Double o1 = operand.pop();
            Double o2;
            char sign = operateSigns.pop();
            if (!operateSigns.isEmpty() && comparePriority(sign, operateSigns.peek()) < 0) {
                o2 = Double.valueOf(this.calculateExpression(operateSigns, operand));
            } else {
                o2 = operand.pop();
            }
            try {
                operand.push(basicCalculate(o1, o2, sign));
            } catch (DividingByZeroException e) {
                throw new DividingByZeroException();
            }
        }
        if (!operand.isEmpty()) {
            ans = operand.pop();
        }
        return ans;
    }

    protected boolean isSign(char c) {
        if (c == '+' || c == '-' || c == '*' || c == '/') return true;
        return false;
    }

    protected Double basicCalculate(Double o1, Double o2, char sign) throws DividingByZeroException {
        switch (sign) {
            case '+':
                return o1 + o2;
            case '-':
                return o1 - o2;
            case '*':
                return o1 * o2;
            default:
                if (o2 == 0) {
                    throw new DividingByZeroException();
                }
                return o1 / o2;
        }
    }

    protected int comparePriority(char s1, char s2) {
        if ((s1 == '*' || s1 == '/') && (s2 == '+' || s2 == '-')) {
            return 1;
        } else if ((s1 == '+' || s1 == '-') && (s2 == '*' || s2 == '/')) {
            return -1;
        }
        return 0;
    }

}
