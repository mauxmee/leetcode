177. Nth Highest Salary
Write a SQL query to get the nth highest salary from the Employee table.

+----+--------+
| Id | Salary |
+----+--------+
| 1  | 100    |
| 2  | 200    |
| 3  | 300    |
+----+--------+
For example, given the above Employee table, the nth highest salary where n = 2 is 200.
If there is no nth highest salary, then the query should return null.

+------------------------+
| getNthHighestSalary(2) |
+------------------------+
| 200                    |
+------------------------+
通过次数115,231提交次数249,868

--- my solution 1
-- CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
-- BEGIN
-- RETURN (
--         # Write your MySQL query statement below.
--       SELECT DISTINCT Salary FROM Employee WHERE Salary IS NOT NULL ORDER BY Salary DESC LIMIT N,1;
-- );
-- END

-- accepted solution from web
/*
 Runtime: 262 ms, faster than 93.90% of MySQL online submissions for Nth Highest Salary.
 */
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
declare m INT;
set m=n-1;
RETURN (
    # Write your MySQL query statement below.
      select (select distinct Salary from Employee WHERE Salary IS NOT NULL order by Salary desc Limit m,1 )
        as getNthHighestSalary
    );
END