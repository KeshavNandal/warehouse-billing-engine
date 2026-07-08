package model;

import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private int id;
    private String customerName;
    private double totalAmount;
    private List<InvoiceItem> items;

    public Invoice(String customerName)
    {
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    public void addItem(InvoiceItem item)
    {
        this.items.add(item);
        this.totalAmount += item.getSubTotal();
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public double getTotalAmount()
    {
        return totalAmount;
    }

    public List<InvoiceItem> getItems()
    {
        return items;
    }

}
