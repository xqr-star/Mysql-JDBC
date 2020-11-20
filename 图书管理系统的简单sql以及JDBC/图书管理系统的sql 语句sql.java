-- 有一个图书管理系统，
-- 请根据要求写出对应的sql
有一个图书管理系统，
包含学生和图书信息，
且图书可以进行分类，
学生可以在一个时间范围内借阅图书，
并在这个时间范围内归还图书。

-- 数据库操作
drop database if exists ebook;
create database ebook character set utf8mb4;

create database if not exists ebook character set utf8mb4;

-- 进入数据库
use ebook;

-- 建立类别表
drop table if exists category;

create table category(
  id int primary key auto_increment,
  name varchar(20)
);

-- 向类别表里面加入数据
insert into 表名(属性) valuse (值)
INSERT INTO category VALUES (1, '历史');

INSERT INTO category VALUES (2, '艺术');

INSERT INTO category VALUES (3, '计算机');

INSERT INTO category VALUES (4, '数学');
insert into category(name) values('小说'); -- 就算我没有插入Int 还是会自增长。

-- 建立学生表

drop table if exists student;
create table student(
 id int primary key auto_increment,
 name varchar(20)
);

-- 向学生表中加入信息
insert into student(name) values('王昭')

INSERT INTO student VALUES (2, '李白');

INSERT INTO student VALUES (3, '貂蝉');

INSERT INTO student VALUES (4, '小乔');

INSERT INTO student VALUES (5, '韩信');

-- 建立图书表
drop table if exists  book;
create table book (
id int primary key auto_increment,
name varchar(20),
author varchar(20),
price decimal(10,2),
category_id int,
foreign key (category_id) references category(id)
);
-- 向图书表中增加信息
INSERT INTO book VALUES (1, '深入理解Java虚拟机', '周志明', 57.90, 3);
insert into book(name,author,price,category_id) values( '西游记', '吴承恩', 30.68, 5);
INSERT INTO book VALUES (3, '儒林外史', '吴敬梓', 18.80, 5);

-- 建立借阅表
drop table if exists borrow_info;
create table borrow_info(
 id int primary key auto_increment,
 book_id int,
 student_id int,
 start_time timestamp null,
 end_time timestamp null,
 foreign key (book_id) references book(id),
 foreign key (student_id) references student(id)
);
-- 向借阅表里面加入信息
INSERT INTO borrow_info VALUES (1, 1, 1, '2018-11-07 18:50:43', '2018-12-07 18:51:01');

INSERT INTO borrow_info VALUES (2, 7, 1, '2019-07-10 10:21:00', '2019-09-10 10:21:00');

INSERT INTO borrow_info VALUES (3, 8, 1, '2019-09-10 10:21:00', '2019-10-10 10:21:00');

INSERT INTO borrow_info VALUES (4, 2, 2, '2019-03-02 16:37:00', '2019-04-02 16:37:00');

INSERT INTO borrow_info VALUES (5, 4, 2, '2019-03-12 14:25:00', '2019-04-12 14:25:00');

INSERT INTO borrow_info VALUES (6, 10, 2, '2019-07-13 16:21:00', '2019-10-13 16:21:00');

INSERT INTO borrow_info VALUES (7, 11, 2, '2019-06-09 09:40:00', '2019-07-09 09:40:00');

INSERT INTO borrow_info VALUES (8, 13, 2, '2019-01-03 15:11:00', '2019-04-03 15:11:00');

INSERT INTO borrow_info VALUES (9, 7, 3, '2019-05-15 13:13:00', '2019-06-15 13:13:00');

INSERT INTO borrow_info VALUES (10, 8, 3, '2019-04-27 13:53:00', '2019-05-27 13:53:00');

INSERT INTO borrow_info VALUES (11, 9, 3, '2019-06-01 11:32:00', '2019-07-01 11:32:00');

INSERT INTO borrow_info VALUES (12, 3, 4, '2019-07-01 09:40:00', '2019-08-01 09:40:00');

INSERT INTO borrow_info VALUES (13, 4, 4, '2019-06-19 11:40:00', '2019-07-19 11:40:00');

INSERT INTO borrow_info VALUES (14, 5, 4, '2019-06-25 09:23:00', '2019-09-25 09:23:00');

INSERT INTO borrow_info VALUES (15, 10, 4, '2019-08-27 15:30:00', '2019-09-27 15:30:00');

INSERT INTO borrow_info VALUES (16, 5, 5, '2019-01-23 14:20:00', '2019-04-23 14:20:00');

INSERT INTO borrow_info VALUES (17, 6, 5, '2019-03-09 10:45:00', '2019-04-09 10:45:00');

INSERT INTO borrow_info VALUES (18, 10, 5, '2019-06-17 11:32:00', '2019-09-17 11:32:00');

-- 表间的关系概览

类别表- id，类别名
学生表-学生id 姓名
书籍表- id，书籍名字，作者名字，价格，类别id（外键）
借阅表- id，书籍id（外键），学生id（外键），开始时间，结束时间


1. 新增貂蝉同学的借阅记录：诗经，从2019年9月25日17:50到2019年10月25日17:50

insert into borrow_info values(19,10,3,'2019-09-25 17:50:00','2019-10-25 17:50:00');

2. 查询计算机分类下的图书借阅信息
类别表 -- 借阅表
select
book.name,
book.id,
borrow_info.student_id,
borrow_info.start_time,
borrow_info.end_time -- 这里结束的时候不能有，
from 
category ,book,borrow_info
where 
category.id = book.category_id -- 连接条件
and  borrow_info.book_id  =  book.id 
and category.name = '计算机';

3. 修改图书《深入理解Java虚拟机》的价格为61.20
update book set price = 61.20 where name = '深入理解Java虚拟机';


4. 删除id最大的一条借阅记录
-- 先选择出来，然后再进行删除操作
-- 再执行删除操作之前，一般都要先执行select 语句，确保要删除的对象能够被正确的查找

select * 
from borrow_info
order by id desc;  

-- 默认是升序的

delete 
from borrow_info 
where id =

( select r.id 
from 
(
select max(id) id 
from borrow_info )r   -- 相当于是有一张中间表r，但是是临时表。

 -- 把表里面数字最大id的选出来
) 
