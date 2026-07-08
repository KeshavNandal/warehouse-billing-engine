package dao;

import model.InvoiceItem;
import model.Invoice;
import db.DBConnection;
import model.Product;

import java.sql.*;

public class InvoiceDAO
{

    public void generateInvoice(Invoice invoice)
    {
        String insertInvoiceSQL = "INSERT INTO invoices (customer_name, total_amount) VALUES(?, ?)";
        String insertItemSQL = "INSERT INTO invoice_items (invoice_id, product_id, quantity, price_per_unit) VALUES (?, ?, ?, ?)";
        String updateStockSQL = "UPDATE products SET stock_quantity = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement invoiceSTMT = null;
        PreparedStatement itemSTMT = null;
        PreparedStatement stockSTMT =null;
        ResultSet generatedKeys = null;

        try
        {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Turn off auto-commit to start a manual transaction block

            invoiceSTMT = conn.prepareStatement(insertInvoiceSQL, Statement.RETURN_GENERATED_KEYS);
            invoiceSTMT.setString(1, invoice.getCustomerName());
            invoiceSTMT.setDouble(2, invoice.getTotalAmount());
            invoiceSTMT.executeUpdate();

            generatedKeys = invoiceSTMT.getGeneratedKeys();
            int invoiceId = -1;
            if (generatedKeys.next())
            {
                invoiceId = generatedKeys.getInt(1); // column index

            }
            else
            {
                throw new SQLException("Failed to retrieve generated Invoice ID.");

            }

            itemSTMT = conn.prepareStatement(insertItemSQL);
            stockSTMT = conn.prepareStatement(updateStockSQL);

            ProductDAO productDAO = new ProductDAO();


            for (InvoiceItem item : invoice.getItems())
            {
                Product liveProduct = productDAO.getProductById(item.getProductId());

                if (liveProduct == null || liveProduct.getStockQuantity() < item.getQuantity()) {
                    throw new SQLException("Transaction Aborted: Insufficient warehouse stock for product ID " + item.getProductId());
                }

                int updatedStock = liveProduct.getStockQuantity() - item.getQuantity();
                stockSTMT.setInt(1, updatedStock);
                stockSTMT.setInt(2, item.getProductId());
                stockSTMT.addBatch();



                itemSTMT.setInt(1, invoiceId);
                itemSTMT.setInt(2, item.getProductId());
                itemSTMT.setInt(3, item.getQuantity());
                itemSTMT.setDouble(4, item.getPricePerUnit());
                itemSTMT.addBatch();
            }

            itemSTMT.executeBatch();
            stockSTMT.executeBatch();
            conn.commit();
            System.out.println("Transaction Committed Successfully: Invoice #" + invoiceId + " generated!");


        }
        catch (SQLException e)
        {
            System.out.println("Transaction failed! Rolling back changes.. " + e.getMessage());
            if(conn != null)
            {
                try
                {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Rollback failed.. " + ex.getMessage());
                }


            }
        }
        finally
        {
            try
            {
                if (generatedKeys != null) generatedKeys.close();
                if (invoiceSTMT != null) invoiceSTMT.close();
                if (itemSTMT != null) itemSTMT.close();
                if(stockSTMT!=null) stockSTMT.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println("Error closing resources: " + e.getMessage());
            }

        }

    }
}

