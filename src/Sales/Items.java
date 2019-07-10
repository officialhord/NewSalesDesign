package Sales;

public class Items {

    String Itemname;

    public Items(String itemname, int itemamount, int quantity, int price) {
        Itemname = itemname;
        Itemamount = itemamount;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItemname() {
        return Itemname;
    }

    public void setItemname(String itemname) {
        Itemname = itemname;
    }

    public int getItemamount() {
        return Itemamount;
    }

    public void setItemamount(int itemamount) {
        Itemamount = itemamount;
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

    int Itemamount, quantity, price;


}
