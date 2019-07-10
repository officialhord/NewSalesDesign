package Sales.SalesScreen;

public class CartItems {

    int total, quantity, price;
    String Itemname;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getItemname() {
        return Itemname;
    }

    public void setItemname(String itemname) {
        Itemname = itemname;
    }

    public CartItems(int total, int quantity, int price, String itemname) {
        this.total = total;
        this.quantity = quantity;
        this.price = price;
        Itemname = itemname;
    }
}
