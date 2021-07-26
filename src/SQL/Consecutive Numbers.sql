180. Consecutive Numbers
SQL架构
Table: Logs
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| id          | int     |
| num         | varchar |
+-------------+---------+
id is the primary key for this table.

Write an SQL query to find all numbers that appear at least three times consecutively.
Return the result table in any order.

The query result format is in the following example:
Logs table:
+----+-----+
| Id | Num |
+----+-----+
| 1  | 1   |
| 2  | 1   |
| 3  | 1   |
| 4  | 2   |
| 5  | 1   |
| 6  | 2   |
| 7  | 2   |
+----+-----+

Result table:
+-----------------+
| ConsecutiveNums |
+-----------------+
| 1               |
+-----------------+
1 is the only number that appears consecutively for at least three times.
通过次数78,575提交次数161,185

--- solutions from the website
/*
Approach: Using DISTINCT and WHERE clause [Accepted]
Algorithm
Consecutive appearing means the Id of the Num are next to each others.
Since this problem asks for numbers appearing at least three times consecutively,
we can use 3 aliases for this table Logs, and then check whether 3 consecutive
numbers are all the same.

SELECT *
FROM
    Logs l1,
    Logs l2,
    Logs l3
WHERE
    l1.Id = l2.Id - 1
    AND l2.Id = l3.Id - 1
    AND l1.Num = l2.Num
    AND l2.Num = l3.Num
;

Id
Num	Id	Num	Id	Num
1	1	2	1	3	1
Note: The first two columns are from l1, then the next two are from l2, and the last two are from l3.
Then we can select any Num column from the above table to get the target data.However, we need to add
    a keyword DISTINCT because it will display a duplicated number if one number
    appears more than 3 times consecutively.
    */
SELECT DISTINCT l1.Num as ConsecutiveNums
FROM Logs l1,
     Logs l2,
     Logs l3
WHERE l1.Id = l2.Id - 1
  AND l2.Id = l3.Id - 1
  AND l1.Num = l2.Num
  AND l2.Num = l3.Num;