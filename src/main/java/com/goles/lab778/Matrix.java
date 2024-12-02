package com.goles.lab778;

import java.util.Random;

public class Matrix implements java.io.Serializable {
    private int[][] matrix;
    
    public Matrix(int rows, int cols) {
        if (rows <= 0 | cols <= 0) throw new IllegalArgumentException();
        matrix = new int[rows][cols];
        Random rnd = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean isNegative = rnd.nextBoolean();
                int sign;
                if (isNegative) sign = -1;
                else sign = 1;
                matrix[i][j] = rnd.nextInt(100) * sign;
            }
        }
    }
    
    public int getRowCount() {
        return matrix.length;
    }
    
    public int getColCount() {
        return matrix[0].length;
    }
    
    public int getElement(int row, int col) {
        return matrix[row][col];
    }
    
    public void setElement(int row, int col, int value) {
        matrix[row][col] = value;
    }
    
    public static int getOddElementSum(Matrix matrix) {
        int sum = 0;
        for (int i = 0; i < matrix.getRowCount(); i++) {
            for (int j = 0; j < matrix.getColCount(); j++) {
                if (matrix.getElement(i, j) % 2 != 0) {
                    sum += matrix.getElement(i, j);
                }
            }
        }
        return sum;
    }
}
