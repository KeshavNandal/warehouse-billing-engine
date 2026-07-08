package model;

public class InvoiceItem {
    private int id;
    private int productId;
    private int quantity;
    private double pricePerUnit;

    public InvoiceItem(int id, int productId, int quantity, double pricePerUnit)
    {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;

    }

    public InvoiceItem(int productId, int quantity, double pricePerUnit) {
        this.productId = productId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;

    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public double getPricePerUnit()
    {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit)
    {
        this.pricePerUnit = pricePerUnit;
    }

    public double getSubTotal()
    {
        return  this.quantity * this.pricePerUnit;
    }

}
