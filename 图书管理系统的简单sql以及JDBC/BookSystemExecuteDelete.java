package org.example;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookSystemExecuteDelete {
    public static void main(String[] args) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            //加载jdbc 驱动程序
            //创建数据库连接 -- 合二为一
            MysqlDataSource ds = new MysqlDataSource();
            ds.setUrl("jdbc:mysql://localhost:3306/ebook?" +
                    "user=root&password=13467289102" +
                    "&useUnicode=true&characterEncoding=UTF-8&" +
                    "useSSL=false");
            connection = ds.getConnection();
            System.out.println(connection); // 打印这个语句的意义是什么

            //创建操作命令对象
            String sql2 = "delete " +
                    "from borrow_info " +
                    "where id =" +
                    "( select r.id " +
                    "from " +
                    "(" +
                    "select max(id) id " +
                    "from borrow_info )r  " +
                    "); ";

            preparedStatement = connection.prepareStatement(sql2);
            //意思是我没有占位符就不执行这个语句吗，我还非得传入一个占位符吗？
            //不过这个本身的意义就是预编译，--不是就算没有占位符还是会预编译。
            System.out.println(preparedStatement);

            int result = preparedStatement.executeUpdate();

            System.out.println(result);
        } catch (Exception e) {
        } finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
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
