public class divideTwoNums {
    public static int divide(int dividend, int divisor) {
        if (divisor == 1) return dividend;
        if (dividend == 0) return 0;

        if (divisor == 0 || dividend < Integer.MIN_VALUE || dividend > Integer.MAX_VALUE ||
                (Integer.MIN_VALUE == dividend && divisor == -1)) {
            return Integer.MAX_VALUE;
        }
        boolean sameSign = (dividend < 0) == (divisor < 0);
        // this is the tricky part: negative has more scope than positive!
        if (dividend > 0) dividend = -dividend;
        if (divisor > 0) divisor = -divisor;
        int res = div(dividend, divisor);
        return sameSign ? res : -res;
    }

    // I came up with the recursive codes, but
    // didn't think of 1. use negative 2. sum + sum > limit condition
    // the other parts are ok
    public static int div(int a, int b) {
        if (a > b) return 0;
        int res = 1;
        int d = b;
        // the second condition is tricky to avoid overflow
        while (a < d + d && d > Integer.MIN_VALUE - d) {
            d += d;
            res += res;
        }
        return res + divide(a - d, b);
    }

    public static void main(String[] args) {
        //int res = divide(10,3);
        //res = divide (100,3);
        int res = divide(-2147483648, 2);
        System.out.println(res);
    }
}
