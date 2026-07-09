import dao.InvoiceDAO;
import dao.ProductDAO;
import model.Product;
import model.Invoice;

import java.util.ArrayList;
import java.util.List;
import model.InvoiceItem;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Initializing Warehouse Management System===");
        Scanner scanner = new Scanner(System.in);
        ProductDAO productDAO = new ProductDAO();
        InvoiceDAO invoiceDAO = new InvoiceDAO();

        while (true)
        {
            System.out.println("\n==================================================");
            System.out.println("               WAREHOUSE MENU                     ");
            System.out.println("==================================================");
            System.out.println("1. View All Products");
            System.out.println("2. Generate New Invoice / Checkout");
            System.out.println("3. Exit System");
            System.out.print("Select an option (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice)
            {
                case 1:
                    System.out.println("\n--- Fetching Current Inventory ---");
                    List <Product> printList = null;
                    printList = productDAO.getAllProducts();
                    if(printList == null || printList.isEmpty())
                    {
                        System.out.println("No products available or failed to fetch database records.");
                        break;
                    }

                    System.out.println("\nID   | NAME                 | SKU        | PRICE    | STOCK");
                    System.out.println("-----------------------------------------------------------");
                    for (Product item : printList)
                    {
                        System.out.printf("%-4d | %-20s | %-10s | %-8.2f | %-5d\n",
                                    item.getId(), item.getName(), item.getSku(), item.getPrice(), item.getStockQuantity());
                    }
                    break;

                case 2:
                    System.out.println("\n--- Initiating Checkout Process ---");
                    System.out.println("Enter Customer Name: ");
                    String customerName = scanner.nextLine();
                    Invoice invoice = new Invoice(customerName);

                    while(true)
                    {
                        System.out.println("Enter Product ID to buy (or -1 to finalize checkout): ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        if(id == -1) break;

                        Product product = productDAO.getProductById(id);

                        if (product == null)
                        {
                            System.out.println("Error: Product ID " + id + " does not exist in our system. Try again.");
                            continue;
                        }

                        else
                        {
                            System.out.print("Enter Quantity for " + product.getName() + ": ");
                            int quantity = scanner.nextInt();
                            scanner.nextLine();

                            if(quantity<=0)
                            {
                                System.out.println("Invalid quantity! , try again");
                                continue;
                            }

                            if(product.getStockQuantity()<quantity)
                            {
                                System.out.println("Insufficient Stock! Available units in warehouse: " + product.getStockQuantity());
                                continue;
                            }
                            InvoiceItem tempItem = new InvoiceItem(id,quantity, product.getPrice());
                            invoice.addItem(tempItem);
                            System.out.println("Added " + quantity + " x " + product.getName() + " to cart.");

                        }
                    }
                    List <InvoiceItem> tempList = invoice.getItems();
                    if(tempList != null && !tempList.isEmpty())
                    {
                        invoiceDAO.generateInvoice(invoice);
                        System.out.println("Your invoice generated successfully!");
                    }
                    else
                    {
                        System.out.println("Checkout cancelled: No items were added to the cart.");
                    }
                    break;
                case 3:
                    System.out.println("Shutting down billing engine. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please select an option between 1 and 3.");



            }



        }







    }
}
