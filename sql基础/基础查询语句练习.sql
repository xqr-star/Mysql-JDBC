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

一般和通配符是哦那个，对于字符数据进行部分匹配查询
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
注意等于判断的是普通的 IS 只能用来判断NULL
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
