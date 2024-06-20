package com.ieening.doexercises.leetcode;

public class Q2288ApplyDiscountToPrices {
    public String discountPrices(String sentence, int discount) {
        StringBuilder s = new StringBuilder();
        int i = 0;
        while (i < sentence.length()) {
            // 判断是否是单词的开头
            if (' ' != sentence.charAt(i)) {
                if ('$' == sentence.charAt(i)) { // 拿到 $ 开头的单词
                    s.append(sentence.charAt(i++));

                    int startI = i;

                    boolean isPrice = true;
                    StringBuilder digits = new StringBuilder();

                    while (i < sentence.length() && ' ' != sentence.charAt(i)) {
                        if (Character.isDigit(sentence.charAt(i))) {
                            digits.append(sentence.charAt(i));
                        } else {
                            isPrice = false;
                        }
                        i++;
                    }
                    if (isPrice && digits.length() > 0) {// 合法价格
                        double price = Double.valueOf(digits.toString());
                        s.append(String.format("%.2f", price - price / 100 * discount));
                    } else {
                        s.append(sentence.substring(startI, i));
                    }
                } else {
                    while (i < sentence.length() && ' ' != sentence.charAt(i)) {
                        s.append(sentence.charAt(i++));
                    }
                }
            } else {
                s.append(sentence.charAt(i++));
            }
        }
        return s.substring(0, s.length() - 1).toString();
    }

    public static void main(String[] args) {
        Q2288ApplyDiscountToPrices solution = new Q2288ApplyDiscountToPrices();
        System.out.println(solution.discountPrices("a$5870426", 64));
    }
}
