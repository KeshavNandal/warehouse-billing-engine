package dao;
import com.mysql.cj.exceptions.ConnectionIsClosedException;
import db.DBConnection;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


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

    public List<Product> getAllProducts()
    {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM products";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try
        {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String sku = rs.getString("sku");
                double price = rs.getDouble("price");
                int stockQuantity = rs.getInt("stock_quantity");

                Product product = new Product(id,name,sku,price,stockQuantity);
                productList.add(product);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error fetching products: " + e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();

            }
            catch (SQLException e)
            {
                System.out.println("Error closing database: " + e.getMessage());
            }
        }

    return productList;
    }
}
