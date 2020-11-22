package org.example;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookSystemExecuteUpdate {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //new BigDecimal(Double.toString(123.123));
        double price = 61.20;
        String name ="深入理解Java虚拟机";
        test(price,name);

    }
    public static void test(double price, String name) throws ClassNotFoundException , SQLException{
        Connection connection = null;
        PreparedStatement statement = null; //PreparedStatement 是 Statement 的子类
        //ResultSet resultSet = null; // 因为我用了executeUpdate 操作，返回值是int 不再许哟啊一个结果集作为返回值
        try {
            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setUrl("jdbc:mysql://localhost:3306/ebook?" +
                    "user=root&password=13467289102" +
                    "&useUnicode=true&characterEncoding=UTF-8&" +
                    "useSSL=false");
            connection =  mysqlDataSource.getConnection();

            System.out.println(connection);
            //执行sql 语句放在前面对这个语句进行预编译
            String sql = "update book set price = ? where name = ?; ";
            //预编译的 传入sql 作为参数
            //发送sql，让数据库预编译：语法分析，执行顺序分析，执行优化
            statement = connection.prepareStatement(sql);

            //创建的是简单的操作命令对象statement --不是我要用的预编译的
            //statement = connection.createStatement();


            //因为预编译的语句是由占位符的，所以我现在要把占位符替换掉
            //设置到第几个占位符，在执行sql 的时候替换

            statement.setBigDecimal(1, BigDecimal.valueOf(price)); // 将double 类型的数值转换成decimal
            statement.setString(2,name);

            //resultSet = statement.executeUpdate(); 使用查询的时候用这个语句
            //新增 修改 删除的时候返回的时候不是一个结果集 返回的是一个整数int  如后面的样子
            // 1 row affected (0.04 sec)
            int r = statement.executeUpdate();

        } catch (Exception e) {
        } finally {
            //反向释放资源
            try {
                //没有result就不在需要对结果集进行释放
                /*if(resultSet != null) {
                    resultSet.close();
                }*/
                if(statement != null) {
                    statement.close();
                }
                if(connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
