package com.ieening.doexercises.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q37SudokuSolver {
    private boolean hasSolved = false;

    public void solveSudoku(char[][] board) {
        boolean[][] horizontalMarked = new boolean[board.length][board[0].length];
        boolean[][] verticalMarked = new boolean[board.length][board[0].length];
        boolean[][] boxMarked = new boolean[board.length][board[0].length];

        List<int[]> blanks = new ArrayList<>(board.length * board[0].length);

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if ('.' == board[x][y]) {
                    blanks.add(new int[] { x, y });
                } else {
                    int markedY = board[x][y] - '1';
                    horizontalMarked[x][markedY] = true;
                    verticalMarked[y][markedY] = true;
                    boxMarked[x / 3 * 3 + y / 3][markedY] = true;
                }
            }
        }

        backtrack(0, blanks, horizontalMarked, verticalMarked, boxMarked, board);
    }

    private void backtrack(int start, List<int[]> blanks, boolean[][] horizontalMarked, boolean[][] verticalMarked,
            boolean[][] boxMarked, char[][] board) {
        // 递归终止条件
        if (start == blanks.size()) {
            hasSolved = true;
            return;
        }

        int x = blanks.get(start)[0], y = blanks.get(start)[1];
        for (int i = 0; i < 9 && !hasSolved; i++) { // 取 1 到 10
            if (!horizontalMarked[x][i] && !verticalMarked[y][i] && !boxMarked[x / 3 * 3 + y / 3][i]) {
                horizontalMarked[x][i] = true;
                verticalMarked[y][i] = true;
                boxMarked[x / 3 * 3 + y / 3][i] = true;
                board[x][y] = (char) (i + '1');
                backtrack(start + 1, blanks, horizontalMarked, verticalMarked, boxMarked, board);
                horizontalMarked[x][i] = false;
                verticalMarked[y][i] = false;
                boxMarked[x / 3 * 3 + y / 3][i] = false;
            }
        }
    }

    public static void main(String[] args) {
        Q37SudokuSolver solution = new Q37SudokuSolver();
        char[][] board = new char[][] { { '5', '3', '.', '.', '7', '.', '.', '.', '.' },
                { '6', '.', '.', '1', '9', '5', '.', '.', '.' }, { '.', '9', '8', '.', '.', '.', '.', '6', '.' },
                { '8', '.', '.', '.', '6', '.', '.', '.', '3' }, { '4', '.', '.', '8', '.', '3', '.', '.', '1' },
                { '7', '.', '.', '.', '2', '.', '.', '.', '6' }, { '.', '6', '.', '.', '.', '.', '2', '8', '.' },
                { '.', '.', '.', '4', '1', '9', '.', '.', '5' }, { '.', '.', '.', '.', '8', '.', '.', '7', '9' } };
        solution.solveSudoku(board);
        System.out.println(Arrays.deepToString(board));
    }
}
