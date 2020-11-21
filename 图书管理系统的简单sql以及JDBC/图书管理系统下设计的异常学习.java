package TestDemo;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) {
        //System.out.println(test(1)); // 执行到最后的finally 的语句
       //System.out.println(test(2));
        System.out.println(test(3));

    }

    public static int test(int i) {
        try {
            if (i == 0)
                throw new SQLException();
            if (i == 1)
                return 1;
            if(i == 2)
                throw new ArrayIndexOutOfBoundsException();

            //或者可以写到这
            return 0;
        }  catch (SQLException throwables) {
            throw new RuntimeException("0");
        } catch (Exception e ) {
            throw new RuntimeException("1"); //下边的语句不执行但 finally还是会执行
        } finally{
            //我们不会在 finally 里面写返回语句 -- 一般会写释放资源啊之类的东西
           //return 2;
            //这里报警告的原因是-- 条件逻辑分支没有覆盖完全
            // 上述三个条件都不满足 最后走到
            //finally 里面结果什么返回值都没有就很奇怪。
            //所以要在上面处理这个情况
            //如果走到 i== 3 进入finally 但里面没有代码和卸载后面一个意思

        }
        //逻辑分支覆盖完整。
        //明确返回条件
        // 逻辑分支的返回值
        //return 0;
    }
}
