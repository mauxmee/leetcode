185. Department Top Three Salaries
SQL架构
Table: Employee

+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| Id           | int     |
| Name         | varchar |
| Salary       | int     |
| DepartmentId | int     |
+--------------+---------+
Id is the primary key for this table.
Each row contains the ID, name, salary, and department of one employee.


Table: Department

+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| Id          | int     |
| Name        | varchar |
+-------------+---------+
Id is the primary key for this table.
Each row contains the ID and the name of one department.


A company executives are interested in seeing who earns the most money in each of the company
departments. A high earner in a department is an employee who has a salary in the top three
unique salaries for that department.

Write an SQL query to find the employees who are high earners in each of the departments.
Return the result table in any order.
The query result format is in the following example:

Employee table:
+----+-------+--------+--------------+
| Id | Name  | Salary | DepartmentId |
+----+-------+--------+--------------+
| 1  | Joe   | 85000  | 1            |
| 2  | Henry | 80000  | 2            |
| 3  | Sam   | 60000  | 2            |
| 4  | Max   | 90000  | 1            |
| 5  | Janet | 69000  | 1            |
| 6  | Randy | 85000  | 1            |
| 7  | Will  | 70000  | 1            |
+----+-------+--------+--------------+

Department table:
+----+-------+
| Id | Name  |
+----+-------+
| 1  | IT    |
| 2  | Sales |
+----+-------+

Result table:
+------------+----------+--------+
| Department | Employee | Salary |
+------------+----------+--------+
| IT         | Max      | 90000  |
| IT         | Joe      | 85000  |
| IT         | Randy    | 85000  |
| IT         | Will     | 70000  |
| Sales      | Henry    | 80000  |
| Sales      | Sam      | 60000  |
+------------+----------+--------+

In the IT department:
- Max earns the highest unique salary
- Both Randy and Joe earn the second-highest unique salary
- Will earns the third-highest unique salary

In the Sales department:
- Henry earns the highest salary
- Sam earns the second-highest salary
- There is no third-highest salary as there are only two employees

-- solution 1
-- SELECT d.Name AS Department, e.Name as Employee, e.Salary
-- FROM Department AS d, Employee AS e
-- WHERE d.Id == e.DepartmentId AND
-- (e.Salary, d.Id) IN (
-- SELECT distinct e.Salary, d.Id
-- FROM Department AS d, Employee AS e
-- GROUP BY e.Salary
-- ORDER BY e.Salary DESC LIMIT 3
-- );

--- solution from official website:
Approach: Using JOIN and sub-query [Accepted]
Algorithm

A top 3 salary in this company means there is no more than 3 salary bigger than itself in the company.

select e1.Name as 'Employee', e1.Salary
from Employee e1
where 3 >
(
    select count(distinct e2.Salary)
    from Employee e2
    where e2.Salary > e1.Salary
)
;

In this code, we count the salary number of which is bigger than e1.Salary.
So the output is as below for the sample data.

| Employee | Salary |
|----------|--------|
| Henry    | 80000  |
| Max      | 90000  |
| Randy    | 85000  |

Then, we need to join the Employee table with Department in order to retrieve
the department information.

MySQL

SELECT d.Name AS 'Department', e1.Name AS 'Employee', e1.Salary
FROM Employee e1
         JOIN
     Department d ON e1.DepartmentId = d.Id
WHERE 3 > (SELECT COUNT(DISTINCT e2.Salary)
           FROM Employee e2
           WHERE e2.Salary > e1.Salary
             AND e1.DepartmentId = e2.DepartmentId
)
;

---- solution 2
If you have access to Dense_Rank (), then the following query will work.

select d.Name as Department, a. Name as Employee, a. Salary
from (
select e.*, dense_rank() over (partition by DepartmentId order by Salary desc) as DeptPayRank
from Employee e
) a
join Department d
on a. DepartmentId = d. Id
where DeptPayRank <=3;