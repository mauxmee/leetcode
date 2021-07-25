175. Combine Two Tables
SQL架构
Table: Person

+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| PersonId    | int     |
| FirstName   | varchar |
| LastName    | varchar |
+-------------+---------+
PersonId is the primary key column for this table.
Table: Address

+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| AddressId   | int     |
| PersonId    | int     |
| City        | varchar |
| State       | varchar |
+-------------+---------+
AddressId is the primary key column for this table.


Write a SQL query for a report that provides the following information for each person in the Person table,
regardless if there is an address for each of those people:
FirstName, LastName, City, State

--------------answer------------------------------

-- tempt 1: not correct, since NULL is allowed for the city and state
SELECT p.FirstName, p.LastName, a.City, a.State
FROM Person as p, Address as a
WHERE p.PersonId = a.PersonId;

-- so must left join
/*Runtime: 281 ms, faster than 79.23% of MySQL online submissions for Combine Two Tables.
Memory Usage: 0B, less than 100.00% of MySQL online submissions for Combine Two Tables.*/
SELECT p.FirstName, p.LastName, a.City, a.State
FROM Person p
LEFT JOIN Address a
ON p.PersonId = a.PersonId;

/*
数据库在通过连接两张或多张表来返回记录时，都会生成一张中间的临时表，然后再将这张临时表返回给用户。
在使用LEFT JOIN时，on和where条件的区别如下：
1、on条件是在生成临时表时使用的条件，它不管on中的条件是否为真，都会返回左边表中的记录。
2、where条件是在临时表生成好后，再对临时表进行过滤的条件。这时已经没有left join的含义（必须返回左边表的记录）了，
条件不为真的就全部过滤掉。
 */