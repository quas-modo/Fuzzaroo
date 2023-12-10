package edu.nju.mutest.example;

public class ComplexExample {

    protected long lborderA = 0L;
    protected long lborderB = 0L;
    protected long lborderC = 0L;

    public ComplexExample(long lborderA, long lborderB, long lborderC) {
        this.lborderA = lborderA;
        this.lborderB = lborderB;
        this.lborderC = lborderC;
    }

    public boolean isTriangle() {
        return isTriangle(this);
    }

    public String getType() {
        return getType(this);
    }

    public static boolean isTriangle(ComplexExample triangle) {
        boolean isTriangle = false;
        if (triangle.lborderA > 0L && triangle.lborderB > 0L && triangle.lborderC > 0L &&
                diffOfBorders(triangle.lborderA, triangle.lborderB) < triangle.lborderC &&
                diffOfBorders(triangle.lborderB, triangle.lborderC) < triangle.lborderA &&
                diffOfBorders(triangle.lborderC, triangle.lborderA) < triangle.lborderB) {
            isTriangle = true;
        }

        return isTriangle;
    }

    public static String getType(ComplexExample triangle) {
        String strType = "Illegal";
        if (isTriangle(triangle)) {
            if (triangle.lborderA == triangle.lborderB && triangle.lborderB == triangle.lborderC) {
                strType = "Regular";
            } else if (triangle.lborderA != triangle.lborderB && triangle.lborderB != triangle.lborderC && triangle.lborderA != triangle.lborderC) {
                strType = "Scalene";
            } else {
                strType = "Isosceles";
            }
        }

        return strType;
    }

    public static long diffOfBorders(long a, long b) {
        return a > b ? a - b : b - a;
    }

    public long[] getBorders() {
        long[] borders = new long[]{this.lborderA, this.lborderB, this.lborderC};
        return borders;
    }

    public String toString() {
        return "Triangle{lborderA=" + this.lborderA + ", lborderB=" + this.lborderB + ", lborderC=" + this.lborderC + '}';
    }

    public static void sort(String[] a) {
        int n = a.length;
        String[] aux = new String[n];
        sort(a, 0, n - 1, 0, aux);
    }

    private static int charAt(String s, int d) {
        assert d >= 0 && d <= s.length();

        return d == s.length() ? -1 : s.charAt(d);
    }

    private static void sort(String[] a, int lo, int hi, int d, String[] aux) {
        if (hi <= lo + 15) {
            insertion(a, lo, hi, d);
        } else {
            int[] count = new int[258];

            int r;
            int c;
            for(r = lo; r <= hi; ++r) {
                c = charAt(a[r], d);
                ++count[c + 2];
            }

            for(r = 0; r < 257; ++r) {
                count[r + 1] += count[r];
            }

            for(r = lo; r <= hi; ++r) {
                c = charAt(a[r], d);
                int var10002 = c + 1;
                int var10004 = count[c + 1];
                count[var10002] = count[c + 1] + 1;
                aux[var10004] = a[r];
            }

            for(r = lo; r <= hi; ++r) {
                a[r] = aux[r - lo];
            }

            for(r = 0; r < 256; ++r) {
                sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
            }

        }
    }

    private static void insertion(String[] a, int lo, int hi, int d) {
        for(int i = lo; i <= hi; ++i) {
            for(int j = i; j > lo && less(a[j], a[j - 1], d); --j) {
                exch(a, j, j - 1);
            }
        }

    }

    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static boolean less(String v, String w, int d) {
        for(int i = d; i < Math.min(v.length(), w.length()); ++i) {
            if (v.charAt(i) < w.charAt(i)) {
                return true;
            }

            if (v.charAt(i) > w.charAt(i)) {
                return false;
            }
        }

        return v.length() < w.length();
    }
}
