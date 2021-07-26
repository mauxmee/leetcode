/*
1777. Product's Price for Each Store
SQL架构
Table: Products

+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| product_id  | int     |
| store       | enum    |
| price       | int     |
+-------------+---------+
(product_id,store) is the primary key for this table.
store is an ENUM of type ('store1', 'store2', 'store3')
where each represents the store this product is available at.
price is the price of the product at this store.

Write an SQL query to find the price of each product in each store.
Return the result table in any order.
The query result format is in the following example

Products table:
+-------------+--------+-------+
| product_id  | store  | price |
+-------------+--------+-------+
| 0           | store1 | 95    |
| 0           | store3 | 105   |
| 0           | store2 | 100   |
| 1           | store1 | 70    |
| 1           | store3 | 80    |
+-------------+--------+-------+
Result table:
+-------------+--------+--------+--------+
| product_id  | store1 | store2 | store3 |
+-------------+--------+--------+--------+
| 0           | 95     | 100    | 105    |
| 1           | 70     | null   | 80     |
+-------------+--------+--------+--------+
Product 0 price's are 95 for store1, 100 for store2 and, 105 for store3.
Product 1 price's are 70 for store1, 80 for store3 and, it's not sold in store2.
通过次数3,987提交次数5,162

 */

--- solution from website
/*
 将行转列，
使用group by分组计算，取每一组中对应情况的通过case when + 聚合函数（min，max,sum,avg）的结合，
求出相同产品在不同商店中的price
case when的使用方法如下：
    CASE 输入参数
    WHEN 可能的情况 THEN
        result
    ELSE
        else_result
    END
我们先来看一下case when的作用

SELECT
    product_id,
    (CASE store WHEN 'store1' THEN price ELSE null END) AS store1,
    (CASE store WHEN 'store2' THEN price ELSE null END) AS store2,
    (CASE store WHEN 'store3' THEN price ELSE null END) AS store3
FROM products
执行之后会出现如下结果:
[0, 95, null, null],
[0, null, null, 105],
[0, null, 100, null],
[1, 70, null, null],
[1, null, null, 80]
 
然后通过group by分组计算，由于group by默认取第一行数据所以通过聚合函数的方式选取指定列中有效的数据
在mysql中的语句执行顺序是
from -> on -> join -> where -> group by -> 聚集函数 -> having -> select ->
 distinct -> union -> order by -> limit
先通过group by将上述结果进行分组，然后通过聚合函数取出有效数据
 */
SELECT product_id,
       MIN(CASE store WHEN 'store1' THEN price ELSE NULL END) as store1,
       MIN(CASE store WHEN 'store2' THEN price ELSE NULL END) as store2,
       MIN(CASE store WHEN 'store3' THEN price ELSE NULL END) as store3
FROM Product
GROUP BY product_id;

-- solution2  from web
/*
 解题思路
通过case when将store1，store2，store3作为三个新的列，有值的地方填写值，没有值的地方使用null，
 最后使用sum将每一列求和，就是一个商品的价格（因为(product_id，store_id)能够唯一确定一个商品的价格）。
 */
select product_id,
       sum(case when store = 'store1' then price end) as store1,
       sum(case when store = 'store2' then price end) as store2,
       sum(case when store = 'store3' then price end) as store3
from products
group by product_id;
