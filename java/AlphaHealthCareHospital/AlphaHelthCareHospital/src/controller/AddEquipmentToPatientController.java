package controller;

import Tm.EquipmentDetail;
import Tm.Equpment;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AddEquipmentToPatientController {
    public AnchorPane patEq;
    public Label lblPatId;
    public ComboBox cmbEqupmentIds;
    public TextField txtName;
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public TextField txtQty;
    public TableView<EquipmentDetail> tblCart;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colQTY;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TableColumn colDelete;
    public Label txtTtl;
    public Button place;
    public Button addCart;
    int temp;
    ObservableList<EquipmentDetail> obList = FXCollections.observableArrayList();
    private int cartSelectedRowForRemove = -1;

    public void initialize() throws SQLException, ClassNotFoundException {
        place.setDisable(true);
        addCart.setDisable(true);
        tblCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));

        tblCart.getColumns().get(5).setCellValueFactory((param) -> {
            ImageView delete = new ImageView("/sample/Images/icons8-delete-bin-30.png");

            delete.setOnMouseClicked(event -> {
                clearOnAction(cartSelectedRowForRemove);
            });

            return new ReadOnlyObjectWrapper(new HBox(10, delete));
        });

        tblCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });


        tblCart.getColumns().setAll(colId, colName, colQTY, colUnitPrice, colTotal, colDelete);

        List<String> eqIds = new ManageEq().getAllEqIds();
        cmbEqupmentIds.getItems().addAll(eqIds);

        cmbEqupmentIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String valueOne = (String) newValue;
            try {
                temp = 0;
                Equpment medi = ManageEq.getEquipment(valueOne);
                txtName.setText(medi.getName());
                temp = medi.getQty();
                txtQtyOnHand.setText(String.valueOf(temp));
                txtUnitPrice.setText(String.valueOf(medi.getPrice()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        int qtyForCustomer = Integer.parseInt(txtQty.getText());
        if (qtyOnHand < qtyForCustomer) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }


        temp = 0;
        temp = Integer.parseInt(txtQtyOnHand.getText()) - Integer.parseInt(txtQty.getText());
        txtQtyOnHand.setText(String.valueOf(temp));
        String description = txtName.getText();

        double unitPrice = Double.parseDouble(txtUnitPrice.getText());

        double total = qtyForCustomer * unitPrice;


        EquipmentDetail tm = new EquipmentDetail(
                cmbEqupmentIds.getValue().toString(),
                description,
                qtyForCustomer,
                unitPrice,
                total
        );

        int rowNumber = isExists(tm);

        if (rowNumber == -1) {
            obList.add(tm);
        } else {
            EquipmentDetail temp = obList.get(rowNumber);
            EquipmentDetail newTm = new EquipmentDetail(
                    temp.getId(),
                    temp.getName(),
                    temp.getQty() + qtyForCustomer,
                    unitPrice,
                    total + temp.getTotal()
            );

            obList.remove(rowNumber);
            obList.add(newTm);
        }
        tblCart.setItems(obList);
        calculateCost();
    }

    private int isExists(EquipmentDetail tm) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getId().equals(obList.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }

    void calculateCost() {
        double ttl = 0;
        for (EquipmentDetail tm : obList
        ) {
            ttl += tm.getTotal();
        }
        txtTtl.setText(ttl + " /=");
    }

    public void clearOnAction(int cartSelectedRowForRemove) {
        if (this.cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        } else {
            obList.remove(this.cartSelectedRowForRemove);
            calculateCost();
            tblCart.refresh();
        }
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        ArrayList<EquipmentDetail> items = new ArrayList<>();
        for (EquipmentDetail tempTm : obList) {
            ManageEq.placeEqDetail(lblPatId.getText(), tempTm.getId(), tempTm.getQty(), tempTm.getTotal());

            Equpment med = ManageEq.getEquipment(tempTm.getId());
            int newQty = 0;
            if (med != null) {
                newQty = Integer.parseInt(String.valueOf(med.getQty())) - tempTm.getQty();
            }
            ManageEq.updateEqQty(tempTm.getId(), newQty);

            items.add(new EquipmentDetail(tempTm.getId(), tempTm.getName(), tempTm.getQty(), tempTm.getUnitPrice(), tempTm.getTotal()));

        }
        new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();
        URL resource = getClass().getResource("../view/AddEquipmentToPatient.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) patEq.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void BackOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddEquipmentToPatient.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) patEq.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void Qty(KeyEvent keyEvent) {
        String regEx = "^[1-9][0-9]{0,2}$";
        String typeText = txtQty.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtQty.getParent().setStyle("-fx-border-color: green");
            place.setDisable(false);
            addCart.setDisable(false);
        } else {
            txtQty.getParent().setStyle("-fx-border-color: red");
            place.setDisable(true);
            addCart.setDisable(true);
        }
    }
}
