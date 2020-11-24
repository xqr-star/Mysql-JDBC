-- 员工表

有一张员工表emp，字段：姓名name，性别sex，部门depart，工资salary。查询以下数据：

作业内容

drop table emp;

create table emp(
 id int primary key auto_increment,
 name varchar(20),
 sex bit default 0,
 depart varchar(20),
 salary decimal(7,2)
);
-- 这个默认值必须是字符型的吗？
--name VARCHAR(20) DEFAULT 'unkown' 这个默认字符串的格式取决于你的属性类型
insert into emp values(1,'xqr',1,'董事长',12300);
insert into emp values(2,'gf',0,'安保部门',2300);
insert into emp values(3,'cpx',0,'市场部门',8200);
insert into emp values(4,'lx',1,'人事部门',7300);
insert into emp values(5,'bllr',1,'行政部门',5600);
insert into emp values(6,'leh',0,'安保部门',2800);
insert into emp values(7,'ert',0,'市场部门',5200);
insert into emp values(8,'lhd',1,'人事部门',7300);
insert into emp values(9,'gf',1,'行政部门',5600);

1、查询男女员工的平均工资
select sex ,avg(salary) avg_salary 
from emp
group by sex;
-- select 中的字段名字要么是group by  中的字段名字要么必须包含在聚合函数中

2、查询各部门的总薪水
select depart,sum(salary) sum_salary
from emp
group by depart;

3、查询总薪水排名第二的部门
select depart,sum(salary) sum_salary
from emp
group by depart
order by sum_salary desc
limit 1,1; 
-- limit 的字段是从 0 开始排序的
SELECT ... FROM table_name [WHERE ...] [ORDER BY ...] LIMIT s, n;

4、查询姓名重复的员工信息
-- 自联结 或者 子链接
select * 
from emp
group by name;  
-- 为什么按照group by 会去除相同的name，是合并分组，但是到底保留哪一个分组？
-- 如果是英文字母的名字的画，会按照字母顺序排序


select * 
from emp 
group by name 
having count(name)>1; -- 但是这个虽然可以筛选出来还是会把后面的信息合并
--并且显示的是第一条数据

所以使用自联结查询


--解决方案：

第一种使用子查询是指嵌入在其他sql 语句的select语句 
-- 1.where 条件 然后使用(not)in 关键字  加上子查询语句
-- 2.子查询语句出现在from  中，相当于把一个子查询当作一个临时表
select * 
from emp
where  name in (
select name
from emp 
group by name
having count(name) >1
);

第二种使用自联结查询

select emp1.name ,emp1.depart ,emp1.id ,emp1.salary 
from emp emp1 ,emp emp2
where emp1.name = emp2.name
and emp1.id != emp2.id;



5、查询各部门薪水大于10000的男性员工的平均薪水

select depart ,avg(salary) avg_salary
from emp
where salary > 10000 and sex = 0
group by depart;

-- 能不能使用having by 语句 
不能因为分组之后就不再显示性别信息了
select depart ,avg(salary) avg_salary
from emp
group by depart; 我的havingBy 语句是对 已经group by 语句的信息进行再次过滤
-- 也就是说 having 子句被限制在已经在SELECT语句中定义的列和聚合表达式上


select * from emp where salary > 10000 ;


-- 其他为了能够执行sql 语句改动过的一些sql

-- update 表名 set 列名 where 
update emp set salary = 11000 where name = 'gf' and depart = '行政部门';
insert into emp values(10,'dfg',1,'行政部门',11008);
insert into emp values(11,'tyu',1,'行政部门',10040);
insert into emp values(12,'dfg',0,'行政部门',11008);
insert into emp values(13,'tyu',0,'行政部门',10040);


select name,avg(salary) avg_salary 
from emp
group by sex; -- 好像不是不能用，而是看到的信息没有意义