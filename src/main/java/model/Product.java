package model;

public class Product {
    private int id;
    private String name;
    private String sku;
    private double price;
    private int stockQuantity;

    public Product()
    {

    }
    public Product(int id, String name, String sku, double price, int stockQuantity )
    {
        this.id = id;
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.price = stockQuantity;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
@Override
    public String toString()
{
    return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", sku='" + sku + '\'' +
            ", price=" + price +
            ", stockQuantity=" + stockQuantity +
            '}';
}



}
