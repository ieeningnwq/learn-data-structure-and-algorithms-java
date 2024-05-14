package com.ieening.doexercises.leetcode;

import java.util.Arrays;

public class Q383RansomNote {
    public boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length()) {
            return false;
        }
        byte[] ransomNoteBytes = ransomNote.getBytes();
        Arrays.sort(ransomNoteBytes);
        byte[] magazineBytes = magazine.getBytes();
        Arrays.sort(magazineBytes);

        int j = 0;
        for (int i = 0; i < ransomNoteBytes.length; i++) {
            while (j < magazineBytes.length && magazineBytes[j] < ransomNoteBytes[i]) {
                j++;
            }
            if ((j < magazineBytes.length && magazineBytes[j] > ransomNoteBytes[i]) || j >= magazineBytes.length) {
                return false;
            }
            j++;
        }
        return true;
    }

    /**
     * 灵活使用数组和字符类型，分析问题的思路很重要，可以把该问题拆分成这样几个问题：
     * 首先是如何判断 ransomNote 是否可以由 magazine 组成。刚开始，自己想到的是排序，然后比较前几位，这样想错了，
     * 后面，修正了一下，变得很麻烦。这里采用了数组，因为这里不允许重复，所以，前面可以看作是采集，后面看作是消耗，
     * 如果消耗大于采集，那么就是不可组成。
     * 
     * @param ransomNote
     * @param magazine
     * @return
     */
    public boolean canConstructCharStatistic(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length()) {
            return false;
        }
        int[] cnt = new int[26];
        for (char c : magazine.toCharArray()) {
            cnt[c - 'a']++;
        }
        for (char c : ransomNote.toCharArray()) {
            cnt[c - 'a']--;
            if (cnt[c - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }

}
