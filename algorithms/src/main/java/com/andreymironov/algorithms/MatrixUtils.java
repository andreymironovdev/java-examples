package com.andreymironov.algorithms;

import java.util.ArrayDeque;
import java.util.Queue;

public class MatrixUtils {
    /**
     * @param matrix matrix of 0, 1
     * @return number of islands, ie. groups of connected cells (cells are considered connected only for horizontal and vertical directions)
     */
    public static int getIslandsCount(char[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }

        int islandsCount = 0;
        int rowsCount = matrix.length;
        int columnsCount = matrix[0].length;
        boolean[][] visited = new boolean[rowsCount][columnsCount];

        for (int rowIndex = 0; rowIndex < rowsCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnsCount; columnIndex++) {
                char value = matrix[rowIndex][columnIndex];
                if (value == '1' && !visited[rowIndex][columnIndex]) {
                    markIslandAsVisitedBFS(rowIndex, columnIndex, matrix, visited);
                    islandsCount++;
                }
            }
        }

        return islandsCount;
    }

    private static void markIslandAsVisitedDFS(int rowIndex, int columnIndex, char[][] matrix, boolean[][] visited) {
        visited[rowIndex][columnIndex] = true;

        int[] dx = new int[]{1, -1, 0, 0};
        int[] dy = new int[]{0, 0, 1, -1};

        for (int k = 0; k < 4; k++) {
            int newRowIndex = rowIndex + dx[k];
            int newColumnIndex = columnIndex + dy[k];

            if (newRowIndex >= 0 && newRowIndex < matrix.length &&
                    newColumnIndex >= 0 && newColumnIndex < matrix[0].length &&
                    matrix[newRowIndex][newColumnIndex] == '1' && !visited[newRowIndex][newColumnIndex]) {
                markIslandAsVisitedDFS(newRowIndex, newColumnIndex, matrix, visited);
            }
        }
    }

    private static void markIslandAsVisitedBFS(int rowIndex, int columnIndex, char[][] matrix, boolean[][] visited) {
        int[] dx = new int[]{1, -1, 0, 0};
        int[] dy = new int[]{0, 0, 1, -1};

        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{rowIndex, columnIndex});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            visited[current[0]][current[1]] = true;

            for (int k = 0; k < 4; k++) {
                int newRowIndex = current[0] + dx[k];
                int newColumnIndex = current[1] + dy[k];

                if (newRowIndex >= 0 && newRowIndex < matrix.length &&
                        newColumnIndex >= 0 && newColumnIndex < matrix[0].length &&
                        matrix[newRowIndex][newColumnIndex] == '1' && !visited[newRowIndex][newColumnIndex]) {
                    queue.offer(new int[]{newRowIndex, newColumnIndex});
                }
            }
        }

    }
}
