/*
* An ugly number is a positive integer whose prime factors are limited to 2, 3, and 5.

Given an integer n, return the nth ugly number.



Example 1:

Input: n = 10
Output: 12
Explanation: [1, 2, 3, 4, 5, 6, 8, 9, 10, 12] is the sequence of the first 10 ugly numbers.
Example 2:

Input: n = 1
Output: 1
Explanation: 1 has no prime factors, therefore all of its prime factors are limited to 2, 3, and 5.


Constraints:

1 <= n <= 1690
* */

import java.util.*;

public class uglyNumbers {
    public static int nthUglyNumber(int n) {
        if (n == 1) return 1;
        int[] prime = {2, 3, 5};
        // I basically build up the whole numbers before access it.
        // it's slow but works.
        /*
        * Runtime: 659 ms, faster than 5.08% of Java online submissions for Ugly Number II.
Memory Usage: 38.5 MB, less than 31.18% of Java online submissions for Ugly Number II.*/
        Set<Integer> result = new TreeSet<>();

        Map<Integer, Set<Integer>> cache = new HashMap<>(3);
        boolean newData = true;
        while (newData) {
            newData = false;

            for (int i = 0; i < 3; ++i) {
                Set<Integer> si = cache.get(i);
                if (si == null) {
                    si = new TreeSet<>();
                    si.add(prime[i]);
                }
                Set<Integer> newList = new TreeSet<>();
                si.forEach(e ->
                {
                    result.add(e);
                    for (int j = 0; j < 3; ++j) {
                        int v = e * prime[j];
                        if (v < 0) break;
                        newList.add(v);
                    }
                });
                if (!newList.isEmpty()) newData = true;
                cache.put(i, newList);
            }
        }
        cache.forEach((k, v) -> result.addAll(v));
        int count = 0;
        Iterator<Integer> itr = result.iterator();
        while (itr.hasNext()) {
            int value = itr.next();
            if (count == n - 2) return value;
            ++count;
        }
        return 1;
    }

    public static int nthUglyNumber2(int n) {
        if (n <= 0) return 0;
        var twoPointer = 0;
        var threePointer = 0;
        var fivePointer = 0;

        var uglies = new ArrayList<Integer>(n);
        uglies.add(1);
        int count = 1;
        while (count < n) {
            var nextUgly = Math.min(uglies.get(twoPointer) * 2, Math.min(uglies.get(threePointer) * 3, uglies.get(fivePointer) * 5));
            uglies.add(nextUgly);

            if (++count == n)
                return nextUgly;
            if (uglies.get(twoPointer) * 2 == nextUgly)
                ++twoPointer;
            if (uglies.get(threePointer) * 3 == nextUgly)
                ++threePointer;
            if (uglies.get(fivePointer) * 5 == nextUgly)
                ++fivePointer;

        }
        return 1;
    }

    public static void main(String[] args) {
        int result = nthUglyNumber2(110);
        System.out.println(result);
    }
}
