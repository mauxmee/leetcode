package amazon;

import java.util.Arrays;

/*
* 1833. Maximum Ice Cream Bars
难度中等73收藏分享切换为中文接收动态反馈
It is a sweltering summer day, and a boy wants to buy some ice cream bars.
At the store, there are n ice cream bars. You are given an array costs of length n, where costs[i] is the price of the ith ice cream bar in coins. The boy initially has coins coins to spend, and he wants to buy as many ice cream bars as possible.
Return the maximum number of ice cream bars the boy can buy with coins coins.
Note: The boy can buy the ice cream bars in any order.

Example 1:
Input: costs = [1,3,2,4,1], coins = 7
Output: 4
Explanation: The boy can buy ice cream bars at indices 0,1,2,4 for a total price of 1 + 3 + 2 + 1 = 7.
Example 2:
Input: costs = [10,6,8,7,7,8], coins = 5
Output: 0
Explanation: The boy cannot afford any of the ice cream bars.
Example 3:
Input: costs = [1,6,3,1,2,5], coins = 20
Output: 6
Explanation: The boy can buy all the ice cream bars for a total price of 1 + 6 + 3 + 1 + 2 + 5 = 18.

Constraints:
•	costs.length == n
•	1 <= n <= 105
•	1 <= costs[i] <= 105
•	1 <= coins <= 108
*/
public class MaxIceCream {
    public int solution(int[] costs, int coins) {
        // sort the icecreams
        Arrays.sort(costs);
        int sum = 0;
        int i = 0;
        for (; i < costs.length; i++) {
            sum += costs[i];
            if (sum > coins) return i;
        }
        return i;
    }

    public static void main(String[] args) {
        MaxIceCream ic = new MaxIceCream();
        int[] cost1 = {1, 3, 2, 4, 1};
        System.out.println(ic.solution(cost1, 7));
        int[] cost2 = {10, 6, 8, 7, 7, 8};
        System.out.println(ic.solution(cost2, 5));
        int[] cost3 = {1, 6, 3, 1, 2, 5};
        System.out.println(ic.solution(cost3, 20));
    }
}