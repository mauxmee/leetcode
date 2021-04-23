package amazon;

import java.util.*;

/**
 * 200. Number of Islands
 * Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
 * You may assume all four edges of the grid are all surrounded by water.
 * <p>
 * Example 1:
 * Input: grid = [
 * ["1","1","1","1","0"],
 * ["1","1","0","1","0"],
 * ["1","1","0","0","0"],
 * ["0","0","0","0","0"]
 * ]
 * Output: 1
 * Example 2:
 * Input: grid = [
 * ["1","1","0","0","0"],
 * ["1","1","0","0","0"],
 * ["0","0","1","0","0"],
 * ["0","0","0","1","1"]
 * ]
 * Output: 3
 * <p>
 * Constraints:
 * •	m == grid.length
 * •	n == grid[i].length
 * •	1 <= m, n <= 300
 * •	grid[i][j] is '0' or '1'.
 */
public class NumIslands {
    // solution 1 failed sadly for {{'1','1','1'},{'0','1','0'},{'1','1','1'}} case
    public static int solution1(char[][] grid) {
        int count = 0;
        // assume the grid has same number of columns for all rows
        int row = grid.length;
        int col = grid[0].length;
        int[][] flags = new int[row][col];
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (grid[i][j] == '1') {
                    if (i > 0 && grid[i - 1][j] == '1') {
                        flags[i][j] = flags[i - 1][j];
                    } else if (j > 0 && grid[i][j - 1] == '1') {
                        flags[i][j] = flags[i][j - 1];
                    } else {
                        flags[i][j] = count++;
                    }
                }
            }
        }
        return count;
    }

    private static int getIndex(int i, int j) {
        return i << 16 + j;
    }

    private static boolean insertIfFound(int index, int index1, List<Set<Integer>> islands) {
        for (Set<Integer> island : islands) {
            if (island.contains(index)) {
                island.add(index);
                return true;
            }
        }
        return false;
    }

    public static class Point {
        int i;
        int j;

        public Point(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    /*
    * Runtime: 5 ms, faster than 10.39% of Java online submissions for Number of Islands.
Memory Usage: 41.5 MB, less than 52.80% of Java online submissions for Number of Islands.*/

    /* second try: remove flags and change mark to 'V' , result:
    * Runtime: 4 ms, faster than 18.69% of Java online submissions for Number of Islands.
Memory Usage: 41.7 MB, less than 28.53% of Java online submissions for Number of Islands.*/
    /*third try: change to LinkedList, and move out of the loop, result:
    * Runtime: 4 ms, faster than 18.69% of Java online submissions for Number of Islands.
Memory Usage: 41.4 MB, less than 52.60% of Java online submissions for Number of Islands.
*  so the loop can't beat the recursive call.
* */
    public static int solution2(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int count = 0;
        Queue<Point> island = new LinkedList<>();
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (grid[i][j] == '1') {
                    ++count;
                    island.clear();
                    grid[i][j] = 'V';
                    island.add(new Point(i, j));
                    while (!island.isEmpty()) {
                        Point p = island.poll();
                        // top
                        if (p.i > 0 && grid[p.i - 1][p.j] == '1') {
                            grid[p.i - 1][p.j] = 'V';
                            island.add(new Point(p.i - 1, p.j));
                        }
                        // left
                        if (p.j > 0 && grid[p.i][p.j - 1] == '1') {
                            grid[p.i][p.j - 1] = 'V';
                            island.add(new Point(p.i, p.j - 1));
                        }
                        // right
                        if (p.i < row - 1 && grid[p.i + 1][p.j] == '1') {
                            grid[p.i + 1][p.j] = 'V';
                            island.add(new Point(p.i + 1, p.j));
                        }
                        // bottom
                        if (p.j < col - 1 && grid[p.i][p.j + 1] == '1') {
                            grid[p.i][p.j + 1] = 'V';
                            island.add(new Point(p.i, p.j + 1));
                        }
                    }
                }
            }
        }
        return count;
    }

    public static boolean inBound(int R, int C, int row, int col) {
        return (row >= 0 && row < R && col >= 0 && col < C);
    }

    // marks visited objects as 'V'
    public static void DepthFirstSearch(char[][] grid, int R, int C, int i, int j) {
        if ((!inBound(R, C, i, j)) || (grid[i][j] == '0') || (grid[i][j] == 'V')) return;

        grid[i][j] = 'V';

        // visit adjacent '1's
        if (inBound(R, C, i, j + 1)) DepthFirstSearch(grid, R, C, i, j + 1);
        if (inBound(R, C, i + 1, j)) DepthFirstSearch(grid, R, C, i + 1, j);
        if (inBound(R, C, i, j - 1)) DepthFirstSearch(grid, R, C, i, j - 1);
        if (inBound(R, C, i - 1, j)) DepthFirstSearch(grid, R, C, i - 1, j);
    }

    // use Depth First Search algo - Recursive function
    /*
    * Runtime: 1 ms, faster than 100.00% of Java online submissions for Number of Islands.
Memory Usage: 41.6 MB, less than 28.53% of Java online submissions for Number of Islands.*/
    public static int DFS(char[][] grid) {
        int R = grid.length;
        int C = grid[0].length;
        int numIslands = 0;

        // sweep
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                char c = grid[i][j];
                if (c == 'V') {
                    continue;
                }

                // an un-visited '1' means a new island which needs to be marked
                if (c == '1') {
                    numIslands++;
                    DepthFirstSearch(grid, R, C, i, j);
                }
            }
        }
        return numIslands;
    }

    public static void main(String[] args) {
        char[][] raw = {{'1', '1', '1', '1', '0'}, {'1', '1', '0', '1', '0'}, {'1', '1', '0', '0', '0'}, {'0', '0', '0', '0', '0'}};
        int count = solution2(raw);
        char[][] raw2 = {{'1', '1', '1'}, {'0', '1', '0'}, {'1', '1', '1'}};
        count = solution1(raw2);
    }
}
