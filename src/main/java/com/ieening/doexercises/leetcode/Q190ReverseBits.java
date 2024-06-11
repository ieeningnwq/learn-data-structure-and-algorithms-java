package com.ieening.doexercises.leetcode;

/**
 * Q190ReverseBits
 */
public class Q190ReverseBits {
    public int reverseBits(int n) {
        int highZero = 2147483647, lowZero = -2, high = -2147483648, low = 1;
        int i = 1;
        do {
            int highToLow = (n & high) >>> (33 - i - i), lowToHigh = (n & low) << (33 - i - i);
            n &= highZero; // 高位置零
            n &= lowZero; // 低位置零
            n |= highToLow;
            n |= lowToHigh;
            high >>>= 1;
            low <<= 1;
            highZero = (highZero >> 1) | (highZero << 31);
            lowZero = (lowZero << 1) | (lowZero >>> 31);
        } while ((i++) < 16);
        return n;
    }

    public static void main(String[] args) {
        Q190ReverseBits solution = new Q190ReverseBits();
        System.out.println(solution.reverseBits(43261597));

    }
}