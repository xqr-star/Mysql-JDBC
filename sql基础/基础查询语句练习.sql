SHOW DATABASES;
USE `myemployees`;
DESC `departments`;
DESC `employees`;
SELECT CONCAT (first_name,last_name)  AS "姓名" FROM `employees`;
`departments``departments`
SELECT  DISTINCT `department_id` FROM `employees`;
DESC `jobs`;
SHOW COLUMNS  FROM `jobs`;
SELECT  CONCAT(`first_name`,`last_name`,IFNULL ( `commission_pct`,''))
FROM `employees`

# 进阶二 条件查询
# 1.关系/条件表达式
部门编号不是100
SELECT  *
FROM `employees`
WHERE `job_id`<> 100;

工资小于150 00 的姓名
SELECT `last_name`
FROM`employees`
WHERE `salary`<15000;
# 2.逻辑查询
部门编号不是 50 -100 之间的员工的姓名部门编号
SELECT `first_name`,`job_id`
FROM`employees`
WHERE `department_id`< 50 OR `department_id` >100

SELECT `first_name`,`job_id`
FROM`employees`
WHERE  NOT(`department_id`>= 50 AND `department_id` <=100);

# 查询奖金率 》0.03 员工编号在60 -110 之间的
SELECT *
FROM `employees`
WHERE `commission_pct` > 0.03  
OR`employee_id` BETWEEN 60 AND 110

# 3模糊查询
LIKE -- not like  跟在like 的前面 不用说是写在where的后面
IN -- not in
BETWEEN / AND --  not be between/and

一般和通配符搭配，对于字符数据进行部分匹配查询
_单个字符
% 多个字符 0-多个
姓名中含a
SELECT *
FROM `employees`
WHERE last_name LIKE '%a%';

SELECT *
FROM `employees`
WHERE last_name LIKE '%e';

姓名中第三个是X的
SELECT *
FROM `employees`
WHERE last_name LIKE '__x%';

姓名中第二个是_的
SELECT *
FROM `employees`
WHERE last_name LIKE '_\_%';

姓名中既有a 又有e (不会！！！)
SELECT `last_name`
FROM `employees`
WHERE last_name LIKE '%a%' AND last_name LIKE '%e%';

等价于
SELECT `last_name`
FROM `employees`
WHERE last_name LIKE '%a%e%' OR last_name LIKE '%e%a%';



设置$为转移字符
ESCAPE '$';  这句话是 让$ 拥有转义字符的形式和
SELECT *
FROM `employees`
WHERE last_name LIKE '_$_%' ESCAPE '$';

#2. in
查询某字段的值是否属于指定的列表内
IN (常量 1，2，3）
IN ('','') 非数值型的需要用单引号引用起来
NOT IN  (常量 1，2，3）


SELECT `last_name`,`department_id`
FROM `employees`
WHERE  `department_id` IN(30,50,90);

SELECT `last_name`,`department_id`
FROM `employees`
WHERE `department_id` = 30
OR `department_id` = 50
OR `department_id` = 90;


SELECT *
FROM `employees`
WHERE`job_id` NOT IN('SH_CLERK','IT_PROG');


SELECT *
FROM `employees`
WHERE  NOT (`job_id`  = 'IT_PROG'  OR 
`job_id`  = 'SH_CLERK');



SELECT *
FROM `employees`
WHERE `department_id` BETWEEN 30 AND 90;

查询年薪
SELECT `last_name`,`salary`*12*(1+ IFNULL (`commission_pct` ,0)) AS '年薪'
FROM `employees`
WHERE `salary`*12*(1+ IFNULL (`commission_pct` ,0)) BETWEEN 30000 AND 100000

# 4.isnull  is not null
注意等于判断的是空的  只能用来判断 ISNULL
SELECT *
FROM `employees`
WHERE `commission_pct` IS NULL;


SELECT *
FROM `employees`
WHERE `commission_pct` IS NOT NULL;

----------------------
进阶三
排序查询
SELECT
FROM
【WHERE 条件】
ORDER BY 排序列表 可以多个

--
执行顺序
FROM
WHERE
SELECT
ORDER BY 先查出来之后，在进行排序
特点
排序列表可以是单个字段、多个字段、表达式、函数、列数、别名以及以上的组合
升序 ASC -- ascend
降序 DESC -- descend



1.单个字段排序
SELECT *
FROM `employees`
WHERE `employee_id` >120 
ORDER BY `salary`ASC;
2.按表达式排序

SELECT *,`salary`*(1 + `commission_pct`) 年薪
FROM `employees`
WHERE  `commission_pct` IS NOT NULL
ORDER BY `salary`*(1 + `commission_pct`) DESC 

3.按别名排序
（排序可以别名 WHERE 不可以别名的原因就是语句的顺序不一样）
SELECT *,`salary`*(1 + `commission_pct`)*12 年薪
FROM `employees`
WHERE  `commission_pct` IS NOT NULL
ORDER BY 年薪 DESC 

4.函数的结果排序
SELECT LENGTH (`last_name`),`last_name`
FROM `employees`
ORDER BY LENGTH(`last_name`)
-- 或者可以写成
SELECT `last_name`
FROM `employees`
ORDER BY LENGTH(`last_name`)

5.多个字段进行排序
工资升序 部门编号进行降序
就是先按照工资排序 如果工资相同的情况下按照部门编号进行降序排序
SELECT `last_name`,`salary`,`department_id`
FROM `employees`
ORDER BY `salary` ASC ,`department_id` DESC;

6.按列数排序-- 但是语义性差
SELECT *
FROM `employees` 
ORDER BY 2; //按照字典顺序

SELECT *
FROM `employees`
ORDER BY `first_name`;

-- 这里有一个发现就是
SELECT 中的项目不必出现在ORDER BY中）---不建议使用！

SELECT `first_name`,`last_name`
FROM `employees`
ORDER BY LENGTH(`first_name`);
没有特殊出现在SELECT 中的可以出现在
SELECT `last_name`
FROM `employees`
ORDER BY LENGTH(`first_name`);

//ORDER BY 语句中不可以出现AND 要用。分割开
SELECT  `last_name`,`department_id`, `salary` * (1+`commission_pct`)*12 AS '年薪'
FROM `employees`
WHERE `commission_pct` IS NOT NULL 
ORDER BY  '年薪' DESC  , `last_name` ASC;


查询邮箱中包含e的员工信息，并按照邮箱的字节数降序，再按照部门号升序
SELECT *
FROM `employees`
WHERE `email`LIKE '%e%'
ORDER BY LENGTH(`email`)DESC ,`manager_id`ASC;

# 进阶四：常见函数

以下统称为单行函数

字符函数 CONCAT LENGTH  CHAR_LENGTH SUBSTRING  UPPER/LOWER TRIM LEFT/RIGHT LPAD/RPAD
数学函数 ASC CEIL FLOOR ROUND MOD TRUNCATE(截断)
 
日期函数
流程控制函数

以下为分组函数



# 1字符函数
CONCAT ()拼接字符
LENGTH  () 获取字节长度  utf-8 一个汉字是三个字节
	 SELECT LENGTH('hello,') // 6
	 SELECT LENGTH('hello，') // 8
	 SELECT LENGTH('hello,解淇茹') // 15 6 + 3*3
CHAR LENGTH() 获取字符个数
	SELECT  CHAR_LENGTH('hello,解淇茹') //9	
SUBSTRING 截取字符串 SQL的起始所索引是1开始
	  SUBSTR(起始的索引，截取字符长度)
	  SUBSTR(起始的索引) 默认到结束
INSTR 获取字符第一次出现的索引
TRIM 去除前后指定的字符，默认是去除空格
	 SELECT TRIM('     1 2 3    ')
	 SELECT TRIM( 'x' FROM 'xxxx 1x2x3 xxxxx') 指定去除的是x
LPAD RPAD 左填充 /右填充
	SELECT LPAD('xqr',10,6);//一共填充为10个字符用6左填充如果数字小于未进行填充前的，就不填充了
UPPER/LOWER
STRCMP 比较两个字符的大小
	 先比较第一个一样再继续，不一样就不比较了
LEFT/RIGHT 截取子串
	SELECT LEFT('xqr',1)
	SELECT RIGHT('xqr',1)
 

#2.数学函数
ASC 绝对值
CEIL 向上取整，返回》= 参数的最小整数
FLOOR 向下取整 返回 《= 参数的最大整数
ROUND 四舍五入
	 SELECT ROUND(-2,4) //-2
	 SELECT ROUND(-2.5) // -3 注意这个
	 SELECT ROUND(-2.6)
TRUNCATE 截断
MOD 取余
	 a%b = a-a/b*b
	 余数是和被除数的符号相同
	 SELECT MOD(-10,3)//-1
	 SELECT MOD(10,-3) // 1

#3.日期函数
NOW 当前日期带时间
	SELECT NOW();
CURDATE 当前日期不带时间
	SELECT CURDATE();
CURTIME
	SELECT CURTIME(); 当前时间不带日期
DATEDIFF 日期差值 用前面减去后面的
	SELECT DATEDIFF('2021-03-14','2000-10-26')
DATE_FORMAT
	SELECT DATE_FORMAT('2000-10-26','%Y年%m月%d日 %H小时%i分钟%s秒')
STR_TO_DATE 按照指定的格式解析字符串为日期类型
	 SELECT *FROM employees
	 WHERE`hiredate` <STR_TO_DATE( '3/15/1998','%m/%d/%y')
  -- WHERE`hiredate` <STR_TO_DATE( '3/15 1998','%m/%d %y')
# 4 流程控制函数
   IF
   CASE  有两种结构 switch 和多重的IF
	 等值判断  和 区间判断
   
IF 函数 -- 是一定要和select再一起吗？ 
 SELECT IF( `commission_pct`IS NULL,0,`commission_pct`),`commission_pct`
 FROM employees;
CASE 函数 -- 也是一定要在select后面吗？
 CASE 表达式
 WHEN 值1 THEN 结果1
 WHEN 值2 THEN 结果2
 ...
 ELSE 结果n
 END

	
 
 案例
 部门编号是30 工资翻倍
           50  3
           60  4
 显示部门编号
 SELECT `department_id`,`salary`,
 CASE `department_id` 
 WHEN 30 THEN `salary`*2
 WHEN 40 THEN `salary`*3
 WHEN 50 THEN `salary`*4
 ELSE `salary`
 END  AS newSalary
 FROM `employees`;
类似一多重的IF （区间判断）
CASE 
WHEN 条件1 THEN  结果1
WHEN 条件2 THEN  结果2
...
ELSE 结果n
END

案例：如果工资>2000 显示A
	      > 1500  B
	      > 1000 C
	      其他  D
	      注意常量字符加单引号
	      除了数值型都要加引号
	      
 SELECT salary ,
 CASE  -- 区别就是这里没有写那个需要判断的
 WHEN salary >2000 THEN'A'
 WHEN salary > 1500 THEN 'B'
 WHEN salary > 1000 THEN 'C'
 ELSE 'D'
 END 
 AS grade
 FROM `employees`;
 
# 练习题 ：不用记笔记
1. 
SELECT NOW();
SELECT CURDATE();
SELECT CURTIME();
2.将员工的姓名按照首字符排序 并写出姓名长度
SELECT `last_name` AS '长度'
FROM `employees`
ORDER BY SUBSTRING(`last_name`,1,1) ASC ;
3.
不是字段的就要加引号 什么叫字段就是表的属性

   

# 分组函数--聚合函数
分组函数往往将一组数据进行统计计数，最终的到一个值
SUM(字段名) 求和
AVG(字段名)平均数
MAX(字段名)最大值
MIN(字段名)最小值
COUNT(字段名)计算非空字段值的个数 这个需要多用一下

COUNT 重点说 计算非空的个数 
所有函数的使用注意就是函数名() 和()之间不能有空格
1.计算表的行数 COUNT (*)
SELECT COUNT(*) FROM `employees`;
SELECT COUNT(*) FROM `employees` WHERE `department_id` = 30;
2.count (1) -- 补充了解
相当于是在你的表中加入了一个常量列 里面都是1
所有统计的就是一共在多少行里面加了1
SELECT COUNT(1) FROM `employees`;
SELECT COUNT(1) FROM `employees` WHERE `department_id` = 30;
3.搭配DISTINCT 去重
求有员工的部门个数
先把员工表里面的所有部门列出来，然后去重然后统计个数
注意DISTINCT 是关键字不是函数
SELECT COUNT(DISTINCT `department_id`)FROM `employees`;



# 分组查询
SELECT
FROM
WHERE 
GROUP BY 分组列表 （可以是一个或者多个）
HAVING 分组后筛选
ORDER BY 排序列表

执行顺序
FROM
WHERE
GROUP BY
HAVING
SELECT
ORDER BY
这里不太理解
 
特点
1.查询函数往往是分组函数和被分组的字段
2.分组查询中的筛选往往分为两类
 
  


邮箱中包含a的每个部门的最高工资
SELECT MAX(`salary`),`department_id`
FROM`employees`
WHERE `email` LIKE '%a%'
GROUP BY `department_id`;



每个部门的总工资和平均工资
SELECT  `department_id`,SUM(`salary`),AVG(`salary`)
FROM`employees`
GROUP BY`department_id`;


-- 注意下面的如果select 的字段没有在group by中出现
-- 这样的sql语句是无效的
-- 显示出来的id是第一个的
SELECT  `department_id`,SUM(`salary`),AVG(`salary`),`employee_id`
FROM`employees`
GROUP BY`department_id`;

#2 having  分组后的筛选
执行顺序是在GROUP BY的后面

虽然HAVING是 在SELECT之后的但是HAVING 可以用SELECT的别名

查询那个部门的员工数 >5
SELECT  COUNT(*)AS 员工个数,`department_id`
FROM `employees`
GROUP BY `department_id`
HAVING  COUNT(*) >5;


高进阶
SELECT `job_id`,MAX(`salary`) AS '最高工资'
FROM`employees`
WHERE
`commission_pct` IS NOT NULL
GROUP BY `job_id`
HAVING  MAX(`salary`) > 6000
ORDER BY  MAX(`salary`) ASC;
 

# 连接查询
笛卡尔乘积现象 
表1 有m行 表2 有n行
表结果 m*n行
原因：没有将表进行有效的连接

按年代分类：
标准 SQL 92  仅仅支持内连接
     SQL 99 出了全外都支持
按功能分类
	内连接 ：等值连接 非等值连接 自连接
	外连接 ： 左外连接 右外连接 全外连接
	交叉连接

 SQL 92 语法
 内连接
 等值连接
 非等值连接
 自连接
 
 