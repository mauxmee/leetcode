package amazon;

import java.util.*;

/**
 * 973. K Closest Points to Origin
 * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an integer k, return the k closest points to the origin (0, 0).
 * The distance between two points on the X-Y plane is the Euclidean distance (i.e., √(x1 - x2)2 + (y1 - y2)2).
 * You may return the answer in any order. The answer is guaranteed to be unique (except for the order that it is in).
 * <p>
 * Example 1:
 * <p>
 * Input: points = [[1,3],[-2,2]], k = 1
 * Output: [[-2,2]]
 * Explanation:
 * The distance between (1, 3) and the origin is sqrt(10).
 * The distance between (-2, 2) and the origin is sqrt(8).
 * Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
 * We only want the closest k = 1 points from the origin, so the answer is just [[-2,2]].
 * Example 2:
 * Input: points = [[3,3],[5,-1],[-2,4]], k = 2
 * Output: [[3,3],[-2,4]]
 * Explanation: The answer [[-2,4],[3,3]] would also be accepted.
 * <p>
 * Constraints:
 * •	1 <= k <= points.length <= 104
 * •	-104 < xi, yi < 104
 */
public class KClosest {
    /*
    * Runtime: 72 ms, faster than 5.00% of Java online submissions for K Closest Points to Origin.
Memory Usage: 114.7 MB, less than 5.17% of Java online submissions for K Closest Points to Origin.*/
    public static int[][] solution(int[][] points, int k) {
        if (k <= 0) return null;
        TreeMap<Integer, List<int[]>> resultMap = new TreeMap<>();
        for (final int[] p : points) {
            int key = (p[0] * p[0] + p[1] * p[1]);
            List<int[]> v = resultMap.computeIfAbsent(key, k1 -> new ArrayList<>());
            v.add(p);
        }
        int[][] result = new int[k][2];
        int i = 0;
        for (List<int[]> ps : resultMap.values()) {
            if (i >= k) break;
            for (int[] p : ps) {
                result[i++] = p;
                if (i >= k) break;
            }
        }
        return result;
    }

    /*     O(NlogN)
    * Runtime: 23 ms, faster than 58.09% of Java online submissions for K Closest Points to Origin.
Memory Usage: 48.8 MB, less than 8.36% of Java online submissions for K Closest Points to Origin.*/
    public static  int[][] solution2(int[][] points, int k) {
        Arrays.sort(points, Comparator.comparingInt(point -> point[0] * point[0] + point[1] * point[1]));
        return Arrays.copyOfRange(points, 0, k);
    }

    /*  Max heap  / Priority queue
    * Runtime: 19 ms, faster than 72.75% of Java online submissions for K Closest Points to Origin.
Memory Usage: 48 MB, less than 22.07% of Java online submissions for K Closest Points to Origin.*/
    public static int[][] solution3(int[][] points, int K) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((p1, p2) -> Long.compare(distSq(p2), distSq(p1)));

        for (int[] p : points) {
            pq.add(p);

            // we remove top element from the max heap
            // thus ensuring smallest K elements remain
            if (pq.size() > K) {
                pq.remove();
            }
        }

        return pq.toArray(new int[K][]);
    }
    
    private static long distSq(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }

    public static void main(String[] args) {
        int[][] raw = {{3, 3}, {5, -1}, {-2, 4}};
        int[][] result = solution2(raw, 2);
        int[][] raw2 = {{0, 1}, {1, 0}};
        int[][] result2 = solution2(raw2, 2);
        System.out.println("done");
    }
}
