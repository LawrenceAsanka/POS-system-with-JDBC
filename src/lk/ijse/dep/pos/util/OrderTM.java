package lk.ijse.dep.pos.util;

import java.util.Date;

public class OrderTM {
    private String order_id;
    private Date order_date;
    private String ordered_customer_id;
    private String ordered_customer_name;
    private String total;

    public OrderTM() {
    }

    public OrderTM(String order_id, Date order_date, String ordered_customer_id, String ordered_customer_name, String total) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.ordered_customer_id = ordered_customer_id;
        this.ordered_customer_name = ordered_customer_name;
        this.total = total;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public String getOrdered_customer_id() {
        return ordered_customer_id;
    }

    public void setOrdered_customer_id(String ordered_customer_id) {
        this.ordered_customer_id = ordered_customer_id;
    }

    public String getOrdered_customer_name() {
        return ordered_customer_name;
    }

    public void setOrdered_customer_name(String ordered_customer_name) {
        this.ordered_customer_name = ordered_customer_name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderTM{" +
                "order_id='" + order_id + '\'' +
                ", order_date=" + order_date +
                ", ordered_customer_id='" + ordered_customer_id + '\'' +
                ", ordered_customer_name='" + ordered_customer_name + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
