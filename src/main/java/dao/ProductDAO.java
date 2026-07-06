package dao;
import db.DBConnection;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ProductDAO {
    public void saveProduct(Product product)
    {
        String sql = "INSERT INTO products (name, sku, price, stock_quantity) VALUES(?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        try
        {
             conn = DBConnection.getConnection();
             stmt = conn.prepareStatement(sql);
             stmt.setString(1, product.getName());
             stmt.setString(2, product.getSku());
             stmt.setDouble(3, product.getPrice());
             stmt.setInt(4, product.getStockQuantity());
             stmt.executeUpdate();
            System.out.println("Product saved successfully to the database!");
        }
        catch (SQLException e)
        {
            System.out.println("Error saving prodcut" + e.getMessage());
        }
        finally
        {
            try
            {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println("Error closing database resources: " + e.getMessage());
            }
        }

    }
}
