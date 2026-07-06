import dao.ProductDAO;
import model.Product;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Warehouse Billing Engine: Testing Database Insertion ===");

        Product testProduct = new Product("Logitech G Pro Keyboard", "KB-LOGI-GPRO-02", 8999.00, 45);
        ProductDAO productDAO = new ProductDAO();

        System.out.println("Attempting to save product: " + testProduct.getName() + "...");
        productDAO.saveProduct(testProduct);

        System.out.println("==========================================================");
    }
}
