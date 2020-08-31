package lk.ijse.dep.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import lk.ijse.dep.pos.dbConnection.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.dep.pos.util.CustomerTM;
import lk.ijse.dep.pos.util.ItemTM;
import lk.ijse.dep.pos.util.OrderDetailTM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SearchDetailController {
    public Pane pnl_place_order;
    public Label lbl_order_id;
    public Label lbl_order_date;
    public JFXComboBox<CustomerTM> cmb_customer;
    public JFXTextField txt_order_customer_name;
    public JFXComboBox<ItemTM> cmb_item;
    public JFXTextField txt_order_item_name;
    public JFXTextField txt_order_item_stock;
    public JFXTextField txt_order_item_price;
    public JFXTextField txt_order_item_quantity;
    public TableView<OrderDetailTM> tbl_order_details;
    public Label lbl_net_price;
    public JFXButton btn_close;

    public void initializeWithSearchOrderForm(String order_id){

        cmb_item.setDisable(true);
        txt_order_item_name.setEditable(false);
        txt_order_item_stock.setEditable(false);
        txt_order_item_price.setEditable(false);
        txt_order_item_quantity.setEditable(false);

        tbl_order_details.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("item_id"));
        tbl_order_details.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("item_name"));
        tbl_order_details.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("item_unit_price"));
        tbl_order_details.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("item_quantity"));
        tbl_order_details.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("item_total"));

        lbl_order_id.setText(order_id);
        setValuesToCMB(order_id);
        setValuesToCMBItem(order_id);

        tbl_order_details.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OrderDetailTM>() {
            @Override
            public void changed(ObservableValue<? extends OrderDetailTM> observable, OrderDetailTM oldValue, OrderDetailTM newValue) {
                ObservableList<ItemTM> items = cmb_item.getItems();
                for (ItemTM item : items) {
                    if (item.getItem_id().equals(newValue.getItem_id())){
                        cmb_item.getSelectionModel().select(item);
                        txt_order_item_name.setText(item.getItem_name());
                        txt_order_item_stock.setText(String.valueOf(item.getItem_stock()));
                        txt_order_item_price.setText(item.getItem_price());
                        txt_order_item_quantity.setText(String.valueOf(newValue.getItem_quantity()));
                    }
                }

            }
        });



    }

    private void loadAllCustomer(){
        try {
            Statement statement = DBConnection.getInstance().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");
            while (resultSet.next()) {
                String customer_id = resultSet.getString(1);
                String customer_name = resultSet.getString(2);
                String customer_address = resultSet.getString(3);
                String customer_contact = resultSet.getString(4);

                CustomerTM customer = new CustomerTM(customer_id, customer_name, customer_address, customer_contact);
                cmb_customer.getItems().add(customer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setValuesToCMB(String order_id){
        cmb_customer.setDisable(true);
        txt_order_customer_name.setEditable(false);
        loadAllCustomer();
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("SELECT order_date,customer_id,total FROM `order` WHERE order_id=?");
            preparedStatement.setObject(1,order_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String order_date = resultSet.getString(1);
                String customer_id = resultSet.getString(2);
                String total = resultSet.getString(3);

                lbl_order_date.setText(order_date);
                ObservableList<CustomerTM> customers = cmb_customer.getItems();
                for (CustomerTM customer : customers) {
                    if(customer.getCustomer_id().equals(customer_id)){
                        cmb_customer.getSelectionModel().select(customer);
                        txt_order_customer_name.setText(customer.getCustomer_name());
                        lbl_net_price.setText(total);
                    }
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void loadAllItem() {
        Statement statement = null;
        try {
            statement = DBConnection.getInstance().getConnection().createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item");
            while (resultSet.next()) {
                String item_id = resultSet.getString(1);
                String item_name = resultSet.getString(2);
                int item_stock = resultSet.getInt(3);
                String item_price = resultSet.getString(4);

                ItemTM item = new ItemTM(item_id, item_name, item_price, item_stock);
                //load all the items to combo box
                cmb_item.getItems().add(item);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void setValuesToCMBItem(String order_id){
        loadAllItem();
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("SELECT item_id,item_quantity,item_total FROM order_detail WHERE order_id=?");
            preparedStatement.setObject(1,order_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String item_id = resultSet.getString(1);
                String item_quantity = resultSet.getString(2);
                String item_total = resultSet.getString(3);
                ObservableList<ItemTM> items = cmb_item.getItems();
                for (ItemTM item : items) {
                    if(item.getItem_id().equals(item_id)){
                        OrderDetailTM orderDetailTM = new OrderDetailTM(item_id,item.getItem_name(),item.getItem_price(),Integer.parseInt(item_quantity),item_total);
                        tbl_order_details.getItems().add(orderDetailTM);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btn_close_OnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }
}
