package lk.ijse.dep.pos.util;

public class CustomerTM {
    private String customer_id;
    private String customer_name;
    private String customer_address;
    private String customer_contact;

    public CustomerTM() {
    }

    public CustomerTM(String customer_id, String customer_name, String customer_address, String customer_contact) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_address = customer_address;
        this.customer_contact = customer_contact;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_contact() {
        return customer_contact;
    }

    public void setCustomer_contact(String customer_contact) {
        this.customer_contact = customer_contact;
    }

    @Override
    public String toString() {
     return customer_id;
    }

}
