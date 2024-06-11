package com.ieening.doexercises.leetcode;

public class Q3040MaximumNumberOfOperationsWithTheSameScoreII {
    public int maxOperations(int[] nums) {
        int[] startEndIndex = new int[] { 0, nums.length - 1 }, ans = new int[] { 0 }, operations = new int[] { 0 };
        int target = 0;

        operation(nums, startEndIndex, ans, target, operations);

        return ans[0];

    }

    private boolean operation(int[] nums, int[] startEndIndex, int[] ans, int target, int[] operations) {
        int start = startEndIndex[0], end = startEndIndex[1];
        // 判断返回条件
        if (start >= end) // 左右索引任一方被访问过说明结束
            return true;

        if (target == 0) {
            // 前两个元素
            startEndIndex[0] += 2; // 左索引加 2
            operations[0]++; // 操作加 1
            if (ans[0] < operations[0])
                ans[0] = operations[0];
            if (operation(nums, startEndIndex, ans, nums[start] + nums[start + 1], operations))
                return true;
            else {
                startEndIndex[0] -= 2;
                operations[0]--;
            }
            // 后两个元素
            startEndIndex[1] -= 2; // 右索引减 2
            operations[0]++; // 操作加 1
            if (ans[0] < operations[0])
                ans[0] = operations[0];
            if (operation(nums, startEndIndex, ans, nums[end - 1] + nums[end], operations))
                return true;
            else {
                startEndIndex[1] += 2;
                operations[0]--;
            }
            // 前后各一个
            startEndIndex[0] += 1; // 左索引加 1
            startEndIndex[1] -= 1; // 右索引减 1
            operations[0]++; // 操作加 1
            if (ans[0] < operations[0])
                ans[0] = operations[0];
            if (operation(nums, startEndIndex, ans, nums[start] + nums[end], operations))
                return true;
            else {
                startEndIndex[0] -= 1; // 左索引加 1
                startEndIndex[1] += 1; // 右索引减 1
                operations[0]--;
            }
        } else {
            // 前两个元素
            if (nums[start] + nums[start + 1] == target) {
                startEndIndex[0] += 2; // 左索引加 2
                operations[0]++; // 操作加 1
                if (ans[0] < operations[0])
                    ans[0] = operations[0];
                if (operation(nums, startEndIndex, ans, target, operations))
                    return true;
                else {
                    startEndIndex[0] -= 2;
                    operations[0]--;
                }
            }
            // 后两个元素
            if (nums[end] + nums[end - 1] == target) {
                startEndIndex[1] -= 2; // 右索引减 2
                operations[0]++; // 操作加 1
                if (ans[0] < operations[0])
                    ans[0] = operations[0];
                if (operation(nums, startEndIndex, ans, target, operations))
                    return true;
                else {
                    startEndIndex[1] += 2;
                    operations[0]--;
                }
            }
            // 前后各一个
            if (nums[start] + nums[end] == target) {
                startEndIndex[0] += 1; // 左索引加 1
                startEndIndex[1] -= 1; // 右索引减 1
                operations[0]++; // 操作加 1
                if (ans[0] < operations[0])
                    ans[0] = operations[0];
                if (operation(nums, startEndIndex, ans, target, operations))
                    return true;
                else {
                    startEndIndex[0] -= 1; // 左索引加 1
                    startEndIndex[1] += 1; // 右索引减 1
                    operations[0]--;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Q3040MaximumNumberOfOperationsWithTheSameScoreII solution = new Q3040MaximumNumberOfOperationsWithTheSameScoreII();
        int ans = solution.maxOperations(new int[] { 96, 241, 77, 121, 216, 167, 41, 176, 201, 136, 231, 106, 335, 171,
                166, 199,
                218, 210, 205, 175, 258, 22, 315, 213, 138, 199, 312, 132, 8, 201, 136, 128, 209, 282, 55, 227, 110, 9,
                328, 314, 23, 4, 110, 227, 4, 41, 296, 6, 109, 228, 256, 81, 317, 20, 64, 273, 210, 127, 112, 225, 122,
                215, 204, 321, 16, 295, 42, 286, 51, 303, 23, 314, 175, 144, 193, 116, 128, 226, 36, 288, 42, 295, 54,
                283, 329, 207, 130, 10, 333, 135, 202, 131, 206, 303, 274, 63, 260, 222, 320, 17, 233, 155, 182, 171,
                166, 230, 334, 3, 276, 61, 68, 11, 326, 57, 169, 168, 32, 124, 174, 163, 277, 60, 224, 207, 17, 320,
                211, 308, 84, 253, 74, 200, 137, 159, 214, 123, 137, 235, 102, 62, 275, 208, 129, 332, 5, 219, 118, 140,
                197, 34, 106, 97, 198, 139, 233, 104, 197, 140, 54, 300, 216, 121, 278, 255, 82, 139, 198, 21, 275, 192,
                145, 294, 262, 75, 296, 163, 189, 114, 223, 7, 184, 325, 243, 94, 270, 11, 326, 233, 9, 149, 244, 93,
                220, 117, 218, 306, 14, 233, 104, 316, 273, 217, 120, 200, 311, 26, 305, 216, 317, 107, 479, 663, 585,
                560, 543, 462, 726, 775, 192, 924, 75, 393, 661, 458, 657, 230, 20, 121, 32, 137, 124, 213, 89, 248,
                305, 32, 28, 309, 64, 21, 323, 317, 20, 31, 119, 236, 101, 188, 328, 104, 67, 264, 73, 12, 153, 330, 83,
                254, 148, 174, 320, 17, 166, 171, 41, 320, 17, 200, 137, 162, 175, 33, 304, 43, 146, 191, 62, 247, 90,
                316, 3, 334, 59, 88, 249, 37, 128, 209, 201, 136, 259, 78, 283, 4, 333, 73, 264, 147, 190, 44, 293, 279,
                58, 240, 231, 133, 204, 107, 230, 303, 118, 219, 35, 302, 200, 252, 85, 178, 301, 36, 263, 240, 97, 29,
                126, 130, 315, 22, 113, 213, 305, 80, 257, 184, 153, 280, 269, 107, 208, 129, 104, 115, 171, 166, 77,
                234, 103, 259, 78, 34, 4, 335, 2, 327, 8, 194, 143, 177, 160, 129, 208, 183, 154, 49, 24, 313, 301, 111,
                209, 221, 78, 259, 162, 34, 61, 276, 331, 6, 283, 54, 326, 11, 133, 223, 114, 10, 327, 331, 333, 333,
                34, 303, 269, 68, 329, 205, 25, 72, 265, 116, 221, 124, 53, 284, 11, 326, 103, 234, 79, 162, 132, 127,
                151, 186, 119, 77, 260, 138, 95, 242, 2, 161, 296, 207, 130, 170, 260 });
        System.out.println(ans);
    }

}
