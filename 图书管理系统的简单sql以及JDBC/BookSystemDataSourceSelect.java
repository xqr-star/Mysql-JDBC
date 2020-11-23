package org.example;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;

public class BookSystemDataSourceSelect {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        //JDBC 和 mysql 里面都有connection 但是事实上我们用的是JDBC java.sql 的而不是mysql 的
        String name = "计算机";
        Connection connection = null;
        PreparedStatement statement = null; //PreparedStatement 是 Statement 的子类
        ResultSet resultSet = null;
        try {

            //加载JDBC 的驱动包--不再需要

            //创建数据库连接
//        DataSource dataSource = new MysqlDataSource();
//        ((MysqlDataSource) dataSource).setUrl("jdbc:mysql://localhost:3306/ebook?" +
//                "user=root&password=13467289102" +
//                "&useUnicode=true&characterEncoding=UTF-8&" +
//                "useSSL=false");

            // Connection connection = dataSource.getConnection();
            //同时不再需要加载JDBC的驱动程序 ，new MysqlDataSource() 会做到getConnection 的功能
            //为什么强制类型转换，因为是父类引用 .方法调用的是父类的方法，而父类是没有这个方法的
            // 转成对应的子类对象，来调用子类的方法
            //或者写成下面的


            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setUrl("jdbc:mysql://localhost:3306/ebook?" +
                    "user=root&password=13467289102" +
                    "&useUnicode=true&characterEncoding=UTF-8&" +
                    "useSSL=false");
            connection =  mysqlDataSource.getConnection();

            System.out.println(connection);
            //执行sql 语句放在前面对这个语句进行预编译
            String sql = "select " +
                    "book.name," +
                    "book.id," +
                    "borrow_info.student_id," +
                    "borrow_info.start_time," +
                    "borrow_info.end_time " +
                    "from " +
                    "category ,book,borrow_info " +
                    "where " +
                    "category.id = book.category_id " +
                    "and  borrow_info.book_id  =  book.id " +
                    "and category.name = ?; ";
            //预编译的 传入sql 作为参数
            //发送sql，让数据库预编译：语法分析，执行顺序分析，执行优化
            statement = connection.prepareStatement(sql);
            //替换参数
            statement.setString(1,name);
            System.out.println(statement);
            resultSet = statement.executeQuery();

            //  prepared 类的是无参数的 ResultSet executeQuery()
            //而statement 类的是有参数的 ResultSet executeQuery(String sql)

            // 对比使用DriverManager resultSet = statement.executeQuery(sql);

            //处理结果集合
            while(resultSet.next()) {
                 name = resultSet.getString("name");
                int id = resultSet.getInt("id");
                int student_id = resultSet.getInt("student_id");
                Timestamp start_time = resultSet.getTimestamp("start_time");
                Timestamp end_time = resultSet.getTimestamp("end_time");
                //傻瓜式%s 进行填充
                System.out.format("name = %s,id = %d,student_id = &s，start_time = %s,end_time = %s%n",
                        name,id,student_id,start_time,end_time);
            }
        } catch (Exception e) {
            //上面的一些地方出错了
            e.printStackTrace();
            throw new RuntimeException("执行查询功能出错了");
        } finally {
            //不管是否成功执行查询都要反向释放资源
            try {
                if(resultSet != null)
                    resultSet.close();
                if(statement != null)
                    statement.close();
                if(connection != null)
                    connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

    }

}
