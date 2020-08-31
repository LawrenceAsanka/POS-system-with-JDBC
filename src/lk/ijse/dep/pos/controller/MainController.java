package lk.ijse.dep.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import lk.ijse.dep.pos.dbConnection.DBConnection;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.dep.pos.util.CustomerTM;
import lk.ijse.dep.pos.util.ItemTM;
import lk.ijse.dep.pos.util.OrderDetailTM;
import lk.ijse.dep.pos.util.OrderTM;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainController {
    public JFXButton btn_add_customer;
    public JFXButton btn_add_item;
    public JFXButton btn_place_order;
    public JFXButton btn_search_order;
    public JFXButton btn_sign_out;
    public Pane pnl_place_order;
    public Pane pnl_search_oder;
    public Pane pnl_add_item;
    public Pane pnl_add_customer;
    public JFXButton btn_add;
    public Label lbl_customer_id;
    public JFXButton btn_save_customer;
    public JFXTextField txt_customer_name;
    public JFXTextField txt_customer_address;
    public JFXTextField txt_customer_contact;
    public TableView<CustomerTM> tbl_customer_details;
    public JFXButton btn_customer_delete;
    public JFXButton btn_product_add;
    public Label lbl_product_id;
    public JFXTextField txt_item_name;
    public JFXTextField txt_item_price;
    public JFXTextField txt_item_stock;
    public JFXButton btn_item_save;
    public JFXButton btn_item_delete;
    public TableView<ItemTM> tbl_item_details;
    public JFXTextField txt_order_customer_name;
    public JFXComboBox<ItemTM> cmb_item;
    public JFXTextField txt_order_item_name;
    public JFXTextField txt_order_item_stock;
    public JFXTextField txt_order_item_price;
    public JFXTextField txt_order_item_quantity;
    public JFXButton btn_order_add;
    public Label lbl_order_id;
    public Label lbl_order_date;
    public TableView<OrderDetailTM> tbl_order_details;
    public Label lbl_net_price;
    public JFXButton btn_order_delete;
    public JFXComboBox<CustomerTM> cmb_customer;
    public JFXButton btn_order;
    public JFXButton btn_order_place;
    public TableView<OrderTM> tbl_order;
    public TextField txt_search;
    public AnchorPane root;
    private final ArrayList<OrderTM> orderArray = new ArrayList<>();

    public void initialize() {

        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        //customer form initializer starting
        btn_add_customer.requestFocus();
        txt_customer_name.setEditable(false);
        txt_customer_address.setEditable(false);
        txt_customer_contact.setEditable(false);
        btn_save_customer.setDisable(true);
        btn_customer_delete.setDisable(true);

        tbl_customer_details.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        tbl_customer_details.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        tbl_customer_details.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customer_address"));
        tbl_customer_details.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customer_contact"));

        loadAllCustomer();

        tbl_customer_details.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerTM>() {
            @Override
            public void changed(ObservableValue<? extends CustomerTM> observable, CustomerTM oldValue, CustomerTM newValue) {
                CustomerTM selectedCustomer = tbl_customer_details.getSelectionModel().getSelectedItem();

                if (newValue == null) {
                    txt_customer_name.clear();
                    txt_customer_address.clear();
                    txt_customer_contact.clear();
                    txt_customer_name.setEditable(false);
                    txt_customer_address.setEditable(false);
                    txt_customer_contact.setEditable(false);
                    btn_add_customer.requestFocus();
                    lbl_customer_id.setText("CUSTOMER ID");
                    btn_save_customer.setText("SAVE");
                } else {
                    lbl_customer_id.setText(selectedCustomer.getCustomer_id());
                    txt_customer_name.setText(selectedCustomer.getCustomer_name());
                    txt_customer_address.setText(selectedCustomer.getCustomer_address());
                    txt_customer_contact.setText(selectedCustomer.getCustomer_contact());

                    btn_save_customer.setDisable(false);
                    btn_customer_delete.setDisable(false);
                    btn_save_customer.setText("UPDATE");
                    txt_customer_name.setEditable(true);
                    txt_customer_address.setEditable(true);
                    txt_customer_contact.setEditable(true);
                }
            }
        });
        //customer form initializer ending

        //item form initializer starting
        btn_add_item.requestFocus();
        txt_item_name.setEditable(false);
        txt_item_stock.setEditable(false);
        txt_item_price.setEditable(false);
        btn_item_save.setDisable(true);
        btn_item_delete.setDisable(true);

        tbl_item_details.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("item_id"));
        tbl_item_details.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("item_name"));
        tbl_item_details.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("item_price"));
        tbl_item_details.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("item_stock"));

        loadAllItem();

        tbl_item_details.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItemTM>() {
            @Override
            public void changed(ObservableValue<? extends ItemTM> observable, ItemTM oldValue, ItemTM newValue) {
                ItemTM selectedItem = tbl_item_details.getSelectionModel().getSelectedItem();
                if (newValue == null) {
                    return;
                } else {
                    lbl_product_id.setText(selectedItem.getItem_id());
                    txt_item_name.setText(selectedItem.getItem_name());
                    txt_item_price.setText(selectedItem.getItem_price());
                    txt_item_stock.setText(String.valueOf(selectedItem.getItem_stock()));

                    btn_item_save.setText("UPDATE");
                    btn_item_save.setDisable(false);
                    btn_item_delete.setDisable(false);
                    txt_item_name.setEditable(true);
                    txt_item_stock.setEditable(true);
                    txt_item_price.setEditable(true);
                }
            }
        });
        //item form initializer ending

        //place order starting
        btn_order_add.requestFocus();
        cmb_customer.setDisable(true);
        cmb_item.setDisable(true);
        txt_order_customer_name.setEditable(false);
        txt_order_item_name.setEditable(false);
        txt_order_item_stock.setEditable(false);
        txt_order_item_price.setEditable(false);
        txt_order_item_quantity.setEditable(false);
        btn_order.setDisable(true);
        btn_order_delete.setDisable(true);
        btn_order_place.setDisable(true);


        cmb_customer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerTM>() {
            @Override
            public void changed(ObservableValue<? extends CustomerTM> observable, CustomerTM oldValue, CustomerTM newValue) {
                if (newValue == null) {
                    return;
                } else {
                    txt_order_customer_name.setText(newValue.getCustomer_name());
                }
            }
        });

        cmb_item.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItemTM>() {
            @Override
            public void changed(ObservableValue<? extends ItemTM> observable, ItemTM oldValue, ItemTM newValue) {
                if (newValue == null) {
                    return;
                } else {
                    txt_order_item_name.setText(newValue.getItem_name());
                    txt_order_item_stock.setText(newValue.getItem_stock() + "");
                    txt_order_item_price.setText(newValue.getItem_price());
                }
            }
        });

        tbl_order_details.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("item_id"));
        tbl_order_details.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("item_name"));
        tbl_order_details.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("item_unit_price"));
        tbl_order_details.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("item_quantity"));
        tbl_order_details.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("item_total"));

        tbl_order_details.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OrderDetailTM>() {
            @Override
            public void changed(ObservableValue<? extends OrderDetailTM> observable, OrderDetailTM oldValue, OrderDetailTM newValue) {
                if (newValue == null) {
                    return;
                } else {
                    btn_order.setText("UPDATE");
                    btn_order_delete.setText("CANCEL");
                    cmb_item.setDisable(true);

                    ObservableList<ItemTM> items = cmb_item.getItems();
                    for (ItemTM item : items) {
                        if (item.getItem_id().equals(newValue.getItem_id())) {
                            cmb_item.getSelectionModel().select(item);
                            txt_order_item_quantity.setText(newValue.getItem_quantity() + "");

                            Platform.runLater(() -> {
                                txt_order_item_quantity.requestFocus();
                            });


                        }
                    }
                }
            }
        });
        //place order initialize end

        //search order initialize starting
        tbl_order.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("order_id"));
        tbl_order.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("order_date"));
        tbl_order.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("ordered_customer_id"));
        tbl_order.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("ordered_customer_name"));
        tbl_order.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));

        loadAllOrder();

        txt_search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<OrderTM> orders = tbl_order.getItems();
                orders.clear();
                for (OrderTM order : orderArray) {
                    if (order.getOrder_id().contains(newValue) ||
                            order.getOrder_date().toString().contains(newValue) ||
                            order.getOrdered_customer_id().contains(newValue) ||
                            order.getOrdered_customer_name().contains(newValue) ||
                            order.getTotal().contains(newValue)) {
                        orders.add(order);
                    }
                }
            }
        });
        //search order initialize end

    }

    //customer form starting
    public void handleButtonAction(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btn_add_customer) {
            pnl_add_customer.toFront();

        } else if (actionEvent.getSource() == btn_add_item) {
            pnl_add_item.toFront();

        } else if (actionEvent.getSource() == btn_place_order) {
            pnl_place_order.toFront();

        } else if (actionEvent.getSource() == btn_search_order) {
            pnl_search_oder.toFront();

        }
    }


    public void add_customer_OnAction(ActionEvent actionEvent) {

        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("SELECT customer_id FROM customer ORDER BY customer_id DESC LIMIT 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                lbl_customer_id.setText("C001");
            } else {
                String lastCustomerID = resultSet.getString(1);
                String substring = lastCustomerID.substring(1, 4);
                int newCustomerID = Integer.parseInt(substring) + 1;
                if (newCustomerID < 10) {
                    lbl_customer_id.setText("C00" + newCustomerID);
                } else if (newCustomerID < 100) {
                    lbl_customer_id.setText("C0" + newCustomerID);
                } else {
                    lbl_customer_id.setText("C" + newCustomerID);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        txt_customer_name.clear();
        txt_customer_contact.clear();
        txt_customer_contact.clear();
        txt_customer_name.setEditable(true);
        txt_customer_address.setEditable(true);
        txt_customer_contact.setEditable(true);
        btn_save_customer.setDisable(false);
        btn_customer_delete.setDisable(false);
        btn_save_customer.setDisable(false);
        btn_customer_delete.setDisable(false);
        txt_customer_name.requestFocus();
        btn_save_customer.setText("SAVE");

    }

    public void btn_save_customer_OnAction(ActionEvent actionEvent) {
        String customer_id = lbl_customer_id.getText();
        String customer_name = txt_customer_name.getText();
        String customer_address = txt_customer_address.getText();
        String customer_contact = txt_customer_contact.getText();

        CustomerTM selectedCustomer = tbl_customer_details.getSelectionModel().getSelectedItem();

        if (txt_customer_name.getText().isEmpty() || !txt_customer_name.getText().matches("^[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Please fill the empty fields", ButtonType.OK).showAndWait();
            txt_customer_name.requestFocus();
            txt_customer_name.selectAll();
            return;
        }

        if (txt_customer_address.getText().isEmpty() || !txt_customer_address.getText().matches("^[A-Za-z0-9 ,/]+")) {
            new Alert(Alert.AlertType.ERROR, "Please fill the empty fields", ButtonType.OK).showAndWait();
            txt_customer_address.requestFocus();
            txt_customer_address.selectAll();
            return;
        }
        if (txt_customer_contact.getText().isEmpty() || !txt_customer_contact.getText().matches("^[0-9]{3}(-)[0-9]{7}+")) {
            new Alert(Alert.AlertType.ERROR, "Please fill the empty fields(Eg: xxx-xxxxxxx)", ButtonType.OK).show();
            txt_customer_contact.requestFocus();
            txt_customer_contact.selectAll();
            return;
        }

        if (btn_save_customer.getText().equals("SAVE")) {
            try {
                PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO customer VALUES (?,?,?,?)");
                preparedStatement.setObject(1, customer_id);
                preparedStatement.setObject(2, customer_name);
                preparedStatement.setObject(3, customer_address);
                preparedStatement.setObject(4, customer_contact);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {

                    loadAllCustomer();
                    new Alert(Alert.AlertType.INFORMATION, "Customer Added Successfully", ButtonType.OK).showAndWait();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to update the customer", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                try {
                    PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE customer SET customer_id=?,customer_name=?,customer_address=?,customer_contact=? WHERE customer_id=?");
                    pstm.setObject(1, customer_id);
                    pstm.setObject(2, customer_name);
                    pstm.setObject(3, customer_address);
                    pstm.setObject(4, customer_contact);
                    pstm.setObject(5, selectedCustomer.getCustomer_id());
                    int affectedRows = pstm.executeUpdate();

                    if (affectedRows > 0) {
                        tbl_customer_details.getSelectionModel().clearSelection();
                        tbl_customer_details.getItems().clear();
                        loadAllCustomer();
                        btn_save_customer.setText("SAVE");
                        new Alert(Alert.AlertType.INFORMATION, "Updated Successfully", ButtonType.OK).showAndWait();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                tbl_customer_details.getSelectionModel().clearSelection();
                btn_save_customer.setText("SAVE");
            }
        }
        lbl_customer_id.setText("CUSTOMER ID");
        txt_customer_name.clear();
        txt_customer_address.clear();
        txt_customer_contact.clear();
        txt_customer_name.setEditable(false);
        txt_customer_address.setEditable(false);
        txt_customer_contact.setEditable(false);
        btn_add_customer.requestFocus();
    }

    public void loadAllCustomer() {
        try {
            Statement statement = DBConnection.getInstance().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");
            tbl_customer_details.getItems().clear();
            while (resultSet.next()) {
                String customer_id = resultSet.getString(1);
                String customer_name = resultSet.getString(2);
                String customer_address = resultSet.getString(3);
                String customer_contact = resultSet.getString(4);

                CustomerTM customer = new CustomerTM(customer_id, customer_name, customer_address, customer_contact);
                tbl_customer_details.getItems().add(customer);

                //load all customers to combo box
                cmb_customer.getItems().add(customer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btn_customer_delete_OnAction(ActionEvent actionEvent) {
        CustomerTM selectedCustomer = tbl_customer_details.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer to delete", ButtonType.CLOSE).showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete the customer", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                try {
                    PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM customer WHERE customer_id=?");
                    preparedStatement.setObject(1, selectedCustomer.getCustomer_id());
                    int affectedRows = preparedStatement.executeUpdate();
                    if (affectedRows > 0) {
                        tbl_customer_details.getSelectionModel().clearSelection();
                        tbl_customer_details.getItems().clear();
                        loadAllCustomer();
                        new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully").showAndWait();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            } else {
                tbl_customer_details.getSelectionModel().clearSelection();
            }
        }
    }
    //customer form ending

    //item form starting
    public void btn_product_add_OnAction(ActionEvent actionEvent) {
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("SELECT item_id FROM item ORDER BY item_id DESC LIMIT 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                lbl_product_id.setText("I001");
            } else {
                String lastItem = resultSet.getString(1);
                String substringItem = lastItem.substring(1, 4);
                int item = Integer.parseInt(substringItem) + 1;
                if (item < 10) {
                    lbl_product_id.setText("I00" + item);
                } else if (item < 100) {
                    lbl_product_id.setText("I0" + item);
                } else {
                    lbl_product_id.setText("I" + item);
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        txt_item_name.requestFocus();
        txt_item_name.setEditable(true);
        txt_item_stock.setEditable(true);
        txt_item_price.setEditable(true);
        btn_item_save.setDisable(false);
        btn_item_delete.setDisable(false);

    }


    public void btn_item_save_OnAction(ActionEvent actionEvent) {
        String item_id = lbl_product_id.getText();
        String item_name = txt_item_name.getText();
        String item_price = txt_item_price.getText();
        String item_stock = txt_item_stock.getText();
        ItemTM selectedItem = tbl_item_details.getSelectionModel().getSelectedItem();

        if (txt_item_name.getText().isEmpty() || !txt_item_name.getText().matches("^[A-Za-z0-9 -]+")) {
            new Alert(Alert.AlertType.ERROR, "Please fill the empty fields", ButtonType.OK).showAndWait();
            txt_item_name.requestFocus();
            txt_item_name.selectAll();
            return;
        }
        if (txt_item_price.getText().isEmpty() || !txt_item_price.getText().matches("^[0-9]+(.)[0]{2}")) {
            new Alert(Alert.AlertType.ERROR, "Please fill the empty fields", ButtonType.OK).showAndWait();
            txt_item_price.requestFocus();
            txt_item_price.selectAll();
            return;
        }
        if (txt_item_stock.getText().isEmpty() || !txt_item_stock.getText().matches("^[0-9]+")) {
            new Alert(Alert.AlertType.ERROR, "Please fill the empty fields", ButtonType.OK).showAndWait();
            txt_item_stock.requestFocus();
            txt_item_stock.selectAll();
            return;
        }

        if (btn_item_save.getText().equals("SAVE")) {
            try {
                PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
                preparedStatement.setObject(1, item_id);
                preparedStatement.setObject(2, item_name);
                preparedStatement.setObject(3, item_stock);
                preparedStatement.setObject(4, item_price);
                int affectedRows = preparedStatement.executeUpdate();
                tbl_item_details.getItems().clear();
                if (affectedRows > 0) {
                    loadAllItem();
                    new Alert(Alert.AlertType.INFORMATION, "Product added successfully", ButtonType.OK).showAndWait();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to update the product", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                try {
                    PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE item SET item_id=?,item_name=?,item_stock=?,item_unit_price=? WHERE item_id=?");
                    pstm.setObject(1, lbl_product_id.getText());
                    pstm.setObject(2, txt_item_name.getText());
                    pstm.setObject(3, txt_item_stock.getText());
                    pstm.setObject(4, txt_item_price.getText());
                    pstm.setObject(5, selectedItem.getItem_id());
                    tbl_item_details.getItems().clear();
                    int rows = pstm.executeUpdate();
                    if (rows > 0) {
                        tbl_item_details.getSelectionModel().clearSelection();
                        btn_item_save.setText("SAVE");
                        loadAllItem();
                        new Alert(Alert.AlertType.INFORMATION, "Updated Successfully", ButtonType.OK).showAndWait();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                tbl_item_details.getSelectionModel().clearSelection();
                btn_item_save.setText("SAVE");
            }
        }
        txt_item_name.clear();
        txt_item_stock.clear();
        txt_item_price.clear();
        lbl_product_id.setText("Product ID");
        txt_item_name.setEditable(false);
        txt_item_stock.setEditable(false);
        txt_item_price.setEditable(false);
        btn_item_save.setDisable(true);
        btn_item_delete.setDisable(true);
        btn_add_item.requestFocus();
    }

    public void btn_item_delete_OnAction(ActionEvent actionEvent) {
        ItemTM selectedItem = tbl_item_details.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer to delete", ButtonType.OK).showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete the customer", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            try {
                PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM item WHERE item_id=?");
                preparedStatement.setObject(1, selectedItem.getItem_id());
                int affectedRows = preparedStatement.executeUpdate();
                tbl_item_details.getItems().clear();
                if (affectedRows > 0) {
                    loadAllItem();
                    tbl_item_details.getSelectionModel().clearSelection();
                    new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully", ButtonType.OK).showAndWait();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            tbl_item_details.getSelectionModel().clearSelection();
        }
        txt_item_name.clear();
        txt_item_stock.clear();
        txt_item_price.clear();
        lbl_product_id.setText("Product ID");
        txt_item_name.setEditable(false);
        txt_item_stock.setEditable(false);
        txt_item_price.setEditable(false);
        btn_item_save.setDisable(true);
        btn_item_delete.setDisable(true);
        btn_item_save.setText("SAVE");
        btn_add_item.requestFocus();
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
            tbl_item_details.getItems().clear();
            while (resultSet.next()) {
                String item_id = resultSet.getString(1);
                String item_name = resultSet.getString(2);
                int item_stock = resultSet.getInt(3);
                String item_price = resultSet.getString(4);

                ItemTM item = new ItemTM(item_id, item_name, item_price, item_stock);
                tbl_item_details.getItems().add(item);
                //load all the items to combo box
                cmb_item.getItems().add(item);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //item form ending

    //place order form start
    public void btn_order_add_OnAction(ActionEvent actionEvent) {
        try {
            Statement statement = DBConnection.getInstance().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT order_id FROM `order` ORDER BY order_id DESC LIMIT 1");
            if (!resultSet.next()) {
                lbl_order_id.setText("O001");
            } else {
                String order_id = resultSet.getString(1);
                String substringId = order_id.substring(1, 4);
                int newId = Integer.parseInt(substringId) + 1;
                if (newId < 10) {
                    lbl_order_id.setText("O00" + newId);
                } else if (newId < 100) {
                    lbl_order_id.setText("O0" + newId);
                } else {
                    lbl_order_id.setText("O" + newId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LocalDate today = LocalDate.now();
        lbl_order_date.setText(today.toString());

        lbl_net_price.setText("000.00");

        cmb_customer.setDisable(false);
        cmb_item.setDisable(false);
        txt_order_item_quantity.setEditable(true);
        btn_order.setDisable(false);
        btn_order_delete.setDisable(false);
        btn_order_place.setDisable(false);

    }

    public void btn_order_OnAction(ActionEvent actionEvent) {

        ItemTM selectedItem = cmb_item.getSelectionModel().getSelectedItem();
        ObservableList<OrderDetailTM> items = tbl_order_details.getItems();
        boolean exit = false;
        int item_quantity = Integer.parseInt(txt_order_item_quantity.getText());

        if (txt_order_item_quantity.getText().isEmpty() || item_quantity > selectedItem.getItem_stock() || item_quantity <= 0 || !txt_order_item_quantity.getText().matches("^[0-9]+")) {
            txt_order_item_quantity.requestFocus();
            return;
        }


        double total = (Double.parseDouble(selectedItem.getItem_price())) * (Double.parseDouble(txt_order_item_quantity.getText()));
        String strDouble = String.format("%.2f", total);

        if (btn_order.getText().equals("ORDER")) {
            for (OrderDetailTM item : items) {
                if (item.getItem_id().equals(selectedItem.getItem_id())) {
                    exit = true;
                    item.setItem_quantity((Integer.parseInt(txt_order_item_quantity.getText()) + item.getItem_quantity()));
                    double total_2 = (Double.parseDouble(selectedItem.getItem_price())) * (Double.parseDouble(String.valueOf(item.getItem_quantity())));
                    String format = String.format("%.2f", total_2);
                    item.setItem_total(format);
                    tbl_order_details.refresh();
                }
            }
            //another way of first one
/*        for (int i = 0; i <tbl_order_details.getItems().size() ; i++) {
            if (tbl_order_details.getItems().get(i).getItem_id().equals(selectedItem.getItem_id())){
                exit=true;
                tbl_order_details.getItems().get(i).setItem_quantity(Integer.parseInt(txt_order_item_quantity.getText())+tbl_order_details.getItems().get(i).getItem_quantity());
                double total_2=(Double.parseDouble(selectedItem.getItem_price())) * (Double.parseDouble(String.valueOf(tbl_order_details.getItems().get(i).getItem_quantity())));
                String format = String.format("%.2f", total_2);
                tbl_order_details.getItems().get(i).setItem_total(format);
                tbl_order_details.refresh();
            }
        }*/

            if (!exit) {
                OrderDetailTM orderDetail = new OrderDetailTM(selectedItem.getItem_id(),
                        selectedItem.getItem_name(),
                        selectedItem.getItem_price(),
                        Integer.parseInt(txt_order_item_quantity.getText()),
                        strDouble);

                tbl_order_details.getItems().add(orderDetail);

            }
            updateAvailableQuantity();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure,to Update", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                availableQuantity();

                for (OrderDetailTM item : items) {
                    if (item.getItem_id().equals(selectedItem.getItem_id())) {
                        item.setItem_quantity(Integer.parseInt(txt_order_item_quantity.getText()));
                        double total_2 = (Double.parseDouble(selectedItem.getItem_price())) * (Double.parseDouble(String.valueOf(item.getItem_quantity())));
                        String format = String.format("%.2f", total_2);
                        item.setItem_total(format);

                    }
                }
                tbl_order_details.refresh();
                btn_order.setText("ORDER");
                cmb_item.setDisable(false);
                tbl_order_details.getSelectionModel().clearSelection();
            } else {
                cmb_item.setDisable(false);
                cmb_item.requestFocus();
                tbl_order_details.getSelectionModel().clearSelection();
            }
        }

        net_total();


        cmb_customer.setDisable(true);
        cmb_item.getSelectionModel().clearSelection();
        txt_order_item_name.clear();
        txt_order_item_stock.clear();
        txt_order_item_price.clear();
        txt_order_item_quantity.clear();
        cmb_item.requestFocus();

    }

    private void net_total() {
        double net_total = 0;

        if (tbl_order_details.getItems().size() == 0) {
            lbl_net_price.setText("000.00");
        } else {
            for (int i = 0; i < tbl_order_details.getItems().size(); i++) {
                net_total += Double.parseDouble(tbl_order_details.getItems().get(i).getItem_total());
            }
            String format_net_total = String.format("%.2f", net_total);
            lbl_net_price.setText(format_net_total);
        }
    }

    private void updateAvailableQuantity() {
        ItemTM selectedItem = cmb_item.getSelectionModel().getSelectedItem();
        ObservableList<OrderDetailTM> items = tbl_order_details.getItems();
        for (OrderDetailTM item : items) {
            if (item.getItem_id().equals(selectedItem.getItem_id())) {
                selectedItem.setItem_stock(selectedItem.getItem_stock() - Integer.parseInt(txt_order_item_quantity.getText()));

            }
        }
    }

    private void availableQuantity() {
        ObservableList<ItemTM> items = cmb_item.getItems();
        OrderDetailTM selectedOrderItem = tbl_order_details.getSelectionModel().getSelectedItem();
        for (ItemTM item : items) {
            if (item.getItem_id().equals(selectedOrderItem.getItem_id())) {
                int newItemQuantity = ((item.getItem_stock() + selectedOrderItem.getItem_quantity()) - Integer.parseInt(txt_order_item_quantity.getText()));
                item.setItem_stock(newItemQuantity);
            }
        }

    }


    public void btn_order_delete_OnAction(ActionEvent actionEvent) {
        OrderDetailTM selectedOrder = tbl_order_details.getSelectionModel().getSelectedItem();
        if (btn_order_delete.getText().equals("DELETE")) {
            if (selectedOrder == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a item to delete", ButtonType.OK).showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    tbl_order_details.getItems().remove(selectedOrder);
                    tbl_order_details.getSelectionModel().clearSelection();
                    tbl_order_details.refresh();

                    ObservableList<ItemTM> items = cmb_item.getItems();
                    for (ItemTM item : items) {
                        if (item.getItem_id().equals(selectedOrder.getItem_id())) {
                            int new_item_stock = selectedOrder.getItem_quantity() + item.getItem_stock();
                            item.setItem_stock(new_item_stock);
                            net_total();

                        }
                    }
                    cmb_customer.setDisable(true);
                    tbl_order_details.getSelectionModel().clearSelection();
                    txt_order_item_name.clear();
                    txt_order_item_stock.clear();
                    txt_order_item_price.clear();
                    txt_order_item_quantity.clear();
                } else {
                    tbl_order_details.getSelectionModel().clearSelection();
                }

                cmb_item.requestFocus();
            }
        } else {
            //btn_cancel
            cmb_item.setDisable(false);
            cmb_item.getSelectionModel().clearSelection();
            tbl_order_details.getSelectionModel().clearSelection();
            txt_order_item_quantity.clear();
            txt_order_item_name.clear();
            txt_order_item_stock.clear();
            txt_order_item_price.clear();
            cmb_item.requestFocus();
            btn_order_delete.setText("DELETE");

        }


    }

    public void btn_order_place_OnAction(ActionEvent actionEvent) {
        if (tbl_order_details.getItems().size() == 0) {
            new Alert(Alert.AlertType.ERROR, "Please do one or more order", ButtonType.OK).showAndWait();
            btn_order_add.requestFocus();
        } else {
            sendDataToOrderTable();
            sendDataToOrderDetailTable();
            updateRealStock();
            loadAllOrder();

            tbl_order_details.getItems().clear();
            btn_order_add.requestFocus();
            cmb_customer.setDisable(true);
            cmb_item.setDisable(true);
            txt_order_customer_name.clear();
            txt_order_customer_name.setEditable(false);
            txt_order_item_name.setEditable(false);
            txt_order_item_stock.setEditable(false);
            txt_order_item_price.setEditable(false);
            txt_order_item_quantity.setEditable(false);
            btn_order.setDisable(true);
            btn_order_delete.setDisable(true);
            btn_order_place.setDisable(true);
            lbl_order_id.setText("ORDER ID");
            lbl_order_date.setText("DATE");
        }

    }

    private void sendDataToOrderTable() {
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO `order` VALUES (?,?,?,?)");
            preparedStatement.setObject(1, lbl_order_id.getText());
            preparedStatement.setObject(2, lbl_order_date.getText());
            preparedStatement.setObject(3, cmb_customer.getSelectionModel().getSelectedItem().getCustomer_id());
            preparedStatement.setObject(4, lbl_net_price.getText());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Order added successfully", ButtonType.OK).showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something error!!!", ButtonType.OK).showAndWait();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void sendDataToOrderDetailTable() {
        try {
            for (int i = 0; i < tbl_order_details.getItems().size(); i++) {
                PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO order_detail VALUES (?,?,?,?)");
                preparedStatement.setObject(1, lbl_order_id.getText());
                ObservableList<OrderDetailTM> itemDetail = tbl_order_details.getItems();
                preparedStatement.setObject(2, tbl_order_details.getItems().get(i).getItem_id());
                preparedStatement.setObject(3, tbl_order_details.getItems().get(i).getItem_quantity());
                preparedStatement.setObject(4, tbl_order_details.getItems().get(i).getItem_total());
                int affectedRows = preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void updateRealStock() {
        ObservableList<ItemTM> items = cmb_item.getItems();
        try {
            for (int i = 0; i < tbl_order_details.getItems().size(); i++) {
                for (ItemTM item : items) {
                    if (item.getItem_id().equals(tbl_order_details.getItems().get(i).getItem_id())) {
                        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE item SET item_stock=? WHERE item_id=?");
                        preparedStatement.setObject(1, item.getItem_stock());
                        preparedStatement.setObject(2, tbl_order_details.getItems().get(i).getItem_id());
                        int affectedRows = preparedStatement.executeUpdate();
                    }
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
//end place order form

    //start search order
    private void loadAllOrder() {
        tbl_order.getItems().clear();
        try {
            String sql = "SELECT `order`.order_id, `order`.order_date, `order`.customer_id, customer.customer_name, `order`.total FROM `order` INNER JOIN customer ON `order`.customer_id = customer.customer_id";
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String order_id = resultSet.getString(1);
                Date order_date = resultSet.getDate(2);
                String ordered_customer_id = resultSet.getString(3);
                String ordered_customer_name = resultSet.getString(4);
                String order_total = resultSet.getString(5);

                //tbl_order.getItems().clear();
                OrderTM orderTM = new OrderTM(order_id, order_date, ordered_customer_id, ordered_customer_name, order_total);
                tbl_order.getItems().add(orderTM);
                orderArray.add(orderTM);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void tbl_order_OnMouseClicked(MouseEvent mouseEvent) {
        if (tbl_order.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (mouseEvent.getClickCount() == 2) {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pos/view/searchDetail.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            SearchDetailController controller = fxmlLoader.getController();
            controller.initializeWithSearchOrderForm(tbl_order.getSelectionModel().getSelectedItem().getOrder_id());
            Scene orderScene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(orderScene);
            stage.centerOnScreen();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

        }
    }

    public void btn_sign_out_OnAction(ActionEvent actionEvent) {
        try {
            Parent root= FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pos/view/login.fxml"));
            Scene mainScene = new Scene(root);
            Stage stage = (Stage) (this.root.getScene().getWindow());
            stage.setScene(mainScene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //end search order
}
