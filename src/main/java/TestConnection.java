import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection  {
    // tested if db is connecting to the application

    public static void main(String[] args) {
        try {
            DriverManager.getConnection("jdbc:mysql://localhost:3306/Warehouse_DB","root","keshav123450");
            System.out.println("Database Connection successful.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
