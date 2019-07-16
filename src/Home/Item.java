package Home;

public class Item {

    String ItemName;
    int quantity, price, total;

    public Item(String itemName, int quantity, int price, int total) {
        ItemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }


    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

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
}
