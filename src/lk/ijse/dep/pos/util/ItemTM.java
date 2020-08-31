package lk.ijse.dep.pos.util;

public class ItemTM {
    private String item_id;
    private String item_name;
    private String item_price;
    private int item_stock;

    public ItemTM() {
    }

    public ItemTM(String item_id, String item_name, String item_price, int item_stock) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_price = item_price;
        this.item_stock = item_stock;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public int getItem_stock() {
        return item_stock;
    }

    public void setItem_stock(int item_stock) {
        this.item_stock = item_stock;
    }

    @Override
    public String toString() {
        return item_id;
    }
}
