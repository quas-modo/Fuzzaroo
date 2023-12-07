package edu.nju.mutest.example;

public class AIRExample {
    private AIRExample() {}

    public static int getElement(int[] array, int index) {
        // 获取指定索引的数组元素
        return array[index];
    }

    public static int findMax(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("数组不能为空");
        }
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static double computeAverage(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("数组不能为空");
        }
        int sum = 0;
        for (int j : array) {
            sum += j;
        }
        return (double) sum / array.length;
    }

    public static boolean contains(int[] array, int value) {
        for (int j : array) {
            if (j == value) {
                return true;
            }
        }
        return false;
    }

    public static int[] copyRange(int[] array, int start, int end) {
        if (array == null || start < 0 || end > array.length || start > end) {
            throw new IllegalArgumentException("参数错误");
        }
        int[] result = new int[end - start];
        System.arraycopy(array, start, result, 0, result.length);
        return result;
    }
}

