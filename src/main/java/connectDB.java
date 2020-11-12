import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDB {
    public static String jdbcURL="jdbc:mysql://localhost:3306/test?useSSL=false";
    public static String jdbcUsername="root";
    public static String jdbcPassword="0964068256";
    public static Connection getConnect(){
        Connection connection= null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
}
