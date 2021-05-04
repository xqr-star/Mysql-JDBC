import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class PreparedStatementBlob {
    public static void main(String[] args) {
        Connection c = null;
        PreparedStatement ps = null;
        FileInputStream is = null;
        try {
            c = MyUnit.getConnection();
            String sql = " insert into star(name,age,photo) values(?,?,?);";
            ps = c.prepareStatement(sql);
            ps.setObject(1, "张国荣");
            ps.setObject(2, 18);

            is = new FileInputStream(new File("lib/gege.jpeg"));
            ps.setBlob(3, is);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            MyUnit.closeResource(c, ps);
        }
    }
}
