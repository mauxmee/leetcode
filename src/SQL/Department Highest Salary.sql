184. Department Highest Salary
SQL架构
The Employee table holds all employees. Every employee has an Id, a salary,
and there is also a column for the department Id.

+----+-------+--------+--------------+
| Id | Name  | Salary | DepartmentId |
+----+-------+--------+--------------+
| 1  | Joe   | 70000  | 1            |
| 2  | Jim   | 90000  | 1            |
| 3  | Henry | 80000  | 2            |
| 4  | Sam   | 60000  | 2            |
| 5  | Max   | 90000  | 1            |
+----+-------+--------+--------------+
The Department table holds all departments of the company.

+----+----------+
| Id | Name     |
+----+----------+
| 1  | IT       |
| 2  | Sales    |
+----+----------+
Write a SQL query to find employees who have the highest salary in each of the departments.
For the above tables, your SQL query should return the following rows (order of rows does not matter).

+------------+----------+--------+
| Department | Employee | Salary |
+------------+----------+--------+
| IT         | Max      | 90000  |
| IT         | Jim      | 90000  |
| Sales      | Henry    | 80000  |
+------------+----------+--------+
Explanation:

Max and Jim both have the highest salary in the IT department and Henry has the highest salary in the Sales department.

通过次数92,224提交次数194,999
-- method 1
/*Runtime: 556 ms, faster than 51.81% of MySQL online submissions for Department Highest Salary.
Memory Usage: 0B, less than 100.00% of MySQL online submissions for Department Highest Salary.*/
SELECT d.Name as Department, e.Name as Employee, e.Salary
FROM Department as D,
     Employee as e
WHERE d.Id = e.DepartmentId
  AND (e.Salary, e.DepartmentId) IN (
    SELECT max(Salary), DepartmentId
    From Employee
    GROUP BY DepartmentId
);

-- method 2
/*Runtime: 491 ms, faster than 86.00% of MySQL online submissions for Department Highest Salary.
Memory Usage: 0B, less than 100.00% of MySQL online submissions for Department Highest Salary.*/
select d.name as department, e.name as employee, salary
from employee e,
     department d
where e.departmentid = d.id
  and (d.name, salary) in (
    select d.name, max(salary)
    from employee e,
         department d
    where e.departmentid = d.id
    group by d.name);

-- Method 3
/*Runtime: 846 ms, faster than 12.32% of MySQL online submissions for Department Highest Salary.
Memory Usage: 0B, less than 100.00% of MySQL online submissions for Department Highest Salary.*/
select d.name as Department, e.name as Employee, e.salary
from Employee e,
     Department d,
     (select max(salary) as max,DepartmentId
      from Employee
      group by DepartmentId) as t
where e.salary = t.max
  and e.DepartmentId = t.DepartmentId
  and e.DepartmentId = d.Id;