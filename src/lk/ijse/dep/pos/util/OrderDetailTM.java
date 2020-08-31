package lk.ijse.dep.pos.util;

public class OrderDetailTM {
    private String item_id;
    private String item_name;
    private String item_unit_price;
    private int item_quantity;
    private String item_total;

    public OrderDetailTM() {
    }

    public OrderDetailTM(String item_id, String item_name, String item_unit_price, int item_quantity, String item_total) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_unit_price = item_unit_price;
        this.item_quantity = item_quantity;
        this.item_total = item_total;
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

    public String getItem_unit_price() {
        return item_unit_price;
    }

    public void setItem_unit_price(String item_unit_price) {
        this.item_unit_price = item_unit_price;
    }

    public int getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(int item_quantity) {
        this.item_quantity = item_quantity;
    }

    public String getItem_total() {
        return item_total;
    }

    public void setItem_total(String item_total) {
        this.item_total = item_total;
    }

    @Override
    public String toString() {
        return item_total;
    }
}
