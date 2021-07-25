176. Second Highest Salary
SQL架构
Write a SQL query to get the second highest salary from the Employee table.

+----+--------+
| Id | Salary |
+----+--------+
| 1  | 100    |
| 2  | 200    |
| 3  | 300    |
+----+--------+
For example, given the above Employee table, the query should return 200 as the second highest salary.
If there is no second highest salary, then the query should return null.

+---------------------+
| SecondHighestSalary |
+---------------------+
| 200                 |
+---------------------+
通过次数229,862提交次数645,992

--------------answer------------------------------
/*select the max from the list of record less than the max value*/
/*Runtime: 161 ms, faster than 95.37% of MySQL online submissions for Second Highest Salary.
Memory Usage: 0B, less than 100.00% of MySQL online submissions for Second Highest Salary.*/
SELECT max(Salary) as SecondHighestSalary
FROM Employee
WHERE Salary < (
    SELECT max(Salary) from Employee
    );

-- method 2
/*
要想获取第二高，需要排序，使用 order by（默认是升序 asc，即从小到大），若想降序则使用关键字 desc
去重，如果有多个相同的数据，使用关键字 distinct 去重
判断临界输出，如果不存在第二高的薪水，查询应返回 null，使用 ifNull（查询，null）方法
起别名，使用关键字 as ...
因为去了重，又按顺序排序，使用 limit（）方法，查询第二大的数据，即第二高的薪水，即 limit(1,1)
（因为默认从0开始，所以第一个1是查询第二大的数，第二个1是表示往后显示多少条数据，这里只需要一条）*/
SELECT IFNULL((
    SELECT DISTINCT Salary
    FROM employee
    ORDER BY Salary DESC LIMIT 1,1), NULL
           )) AS SecondHighestSalary