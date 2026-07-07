import dao.ProductDAO;
import model.Product;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Warehouse Billing Engine: Fetching Inventory ===");

        ProductDAO productDAO = new ProductDAO();
        List <Product> inventory = productDAO.getAllProducts();

        if(inventory.isEmpty())
        {
            System.out.println("No products found in the database table.");
        }
        else
        {
            System.out.println("Successfully pulled " + inventory.size() + " items from MySQL:\n");
        }
        for (Product p : inventory) {
            System.out.println("----------------------------------------");
            System.out.println("ID:             " + p.getId());
            System.out.println("Product Name:   " + p.getName());
            System.out.println("SKU Identifier: " + p.getSku());
            System.out.println("Retail Price:   ₹" + p.getPrice());
            System.out.println("Stock Level:    " + p.getStockQuantity() + " units");
        }

        System.out.println("==========================================================");
    }
}
