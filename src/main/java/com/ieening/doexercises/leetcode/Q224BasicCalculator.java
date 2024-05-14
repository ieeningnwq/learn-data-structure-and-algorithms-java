package com.ieening.doexercises.leetcode;

import java.util.Stack;

class Q224BasicCalculator {
    public int calculate(String s) {
        Stack<String> calculatorStack = new Stack<>();

        int sIndex = 0;
        while (sIndex < s.length()) {
            char c = s.charAt(sIndex++);
            if (c == ' ' || c == '+')
                continue; // 忽略空格和加号
            StringBuilder pushString = new StringBuilder();
            if (c == '-' || Character.isDigit(c)) { // 如果是 -、数字，那么将-和数字放在一起
                pushString.append(c);
                while (sIndex < s.length() && (Character.isDigit(s.charAt(sIndex)) || s.charAt(sIndex) == ' ')) {
                    if (s.charAt(sIndex) != ' ')
                        pushString.append(s.charAt(sIndex));
                    sIndex++;
                }
                calculatorStack.push(pushString.toString());
            } else if (c == ')') { // 括号内计算
                String popString;
                int calculateResult = 0;
                while (!"(".equals(popString = calculatorStack.pop())) {
                    if (!calculatorStack.isEmpty() && "-".equals(calculatorStack.peek())) {
                        calculatorStack.pop();
                        calculateResult -= Integer.parseInt(popString);
                    } else {
                        calculateResult += Integer.parseInt(popString);
                    }
                }
                calculatorStack.push(String.valueOf(calculateResult));
            } else
                calculatorStack.push(String.valueOf(c)); // ( 入栈
        }
        String popString;
        int calculateResult = 0;
        while (!calculatorStack.isEmpty()) {
            popString = calculatorStack.pop();
            if (!calculatorStack.isEmpty() && "-".equals(calculatorStack.peek())) {
                calculatorStack.pop();
                calculateResult -= Integer.parseInt(popString);
            } else {
                calculateResult += Integer.parseInt(popString);
            }
        }
        return calculateResult;
    }

    /**
     * 使用栈记录符号位，然后展开所有括号
     * 
     * @param s
     * @return
     */
    public int calculateBracketExpand(String s) {
        Stack<Integer> signStack = new Stack<>();
        signStack.push(1);
        int sign = 1, sIndex = 0, ret = 0;
        while (sIndex < s.length()) {
            char c = s.charAt(sIndex);
            if (c == ' ') {
                sIndex++;
            }
            if (c == '+') {
                sign = signStack.peek();
                sIndex++;
            } else if (c == '-') {
                sign = -signStack.peek();
                sIndex++;
            } else if (c == '(') {
                signStack.push(sign);
                sIndex++;
            } else if (c == ')') {
                signStack.pop();
                sIndex++;
            } else {
                int num = 0;
                while (sIndex < s.length() && Character.isDigit(c = s.charAt(sIndex))) {
                    num = num * 10 + c - '0';
                    sIndex++;
                }
                ret += sign * num;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        Q224BasicCalculator calculator = new Q224BasicCalculator();
        String s = "(121  - 120+ 10) -((1  +2)-(2-4 +5 + 62-4 -4))+12";
        System.out.println("Result is:" + calculator.calculate(s));
        System.out.println(calculator.calculateBracketExpand(s));
    }
}