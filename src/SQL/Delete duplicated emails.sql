196. Delete Duplicate Emails
SQL Schema
Write a SQL query to delete all duplicate Email entries in a table named Person, keeping only unique Emails based on its smallest Id.

+----+------------------+
| Id | Email            |
+----+------------------+
| 1  | john@example.com |
| 2  | bob@example.com  |
| 3  | john@example.com |
+----+------------------+
Id is the primary key column for this table.
For example, after running your query, the above Person table should have the following rows:

+----+------------------+
| Id | Email            |
+----+------------------+
| 1  | john@example.com |
| 2  | bob@example.com  |
+----+------------------+
Note:

Your output is the whole Person table after executing your sql. Use delete statement.

--------------answer------------------------------
step1: By joining this table with itself on the Email column, we can get the following code

SELECT p1.*
FROM person p1, person p2
WHERE p1.Email = p2.Email;

step2: Then we need to find the bigger id having same Email address with other records.
So we can add a new condition to the WHERE clause like this.
SELECT p1.*
FROM person p1, person p2
WHERE p1.Email = p2.Email AND p1.Id > p2.Id;

step3: so the result query is:
/*Runtime: 1611 ms, faster than 62.60% of MySQL online submissions for Delete Duplicate Emails.
Memory Usage: 0B, less than 100.00% of MySQL online submissions for Delete Duplicate Emails.*/
DELETE p1
FROM person p1, person p2
WHERE p1.Email = p2.Email AND p1.Id > p2.Id;