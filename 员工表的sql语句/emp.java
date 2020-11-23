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

所以使用自联结查询


5、查询各部门薪水大于10000的男性员工的平均薪水

