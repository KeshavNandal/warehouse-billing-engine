import dao.InvoiceDAO;
import dao.ProductDAO;
import model.Product;
import model.Invoice;
import java.util.List;
import model.InvoiceItem;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Warehouse Billing Engine: Testing Invoice ===");

        Invoice TestInvoice = new Invoice("KeshavKumar");

        InvoiceDAO invoiceDAO = new InvoiceDAO();
        ProductDAO productDAO = new ProductDAO();

        Product p1 = productDAO.getProductById(1);
        Product p2 = productDAO.getProductById(3);
        if (p1 == null || p2 == null)
        {
            System.out.println("Error: One or more test products do not exist in the database. Check your IDs!");
            return;
        }

        InvoiceItem TestItem1 = new InvoiceItem(p1.getId(), 13,p1.getPrice());
        InvoiceItem TestItem2 = new InvoiceItem(p2.getId(),7,p2.getPrice());


        TestInvoice.addItem(TestItem1);
        TestInvoice.addItem(TestItem2);

        System.out.println("Sending cart data to transaction engine for " + TestInvoice.getCustomerName() + "...");
        invoiceDAO.generateInvoice(TestInvoice);





        System.out.println("==========================================================");
    }
}
