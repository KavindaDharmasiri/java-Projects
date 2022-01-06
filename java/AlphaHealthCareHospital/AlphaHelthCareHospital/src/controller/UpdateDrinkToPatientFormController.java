package controller;

import Tm.Drink;
import Tm.updateDrinkDetail;
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
import module.finalDrinkDetail;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class UpdateDrinkToPatientFormController {

    static String temp;
    public AnchorPane patDrink;
    public Label lblPatId;
    public TextField txtName;
    public TextField txtQty;
    public TableView<updateDrinkDetail> tblCart;
    public TableColumn colId;
    public TableColumn colQTY;
    public TextField txtNewQty;
    public TableColumn colTotal;
    public TableColumn colDelete;
    public TextField txtQty1;
    public Button backBtn;
    public Button place;
    public Button addCart;
    double pri;

    public static void set(String text) {
        temp = text;
    }

    public void initialize() {
        addCart.setDisable(true);
        place.setDisable(true);
        tblCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("drinkId"));
        tblCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));

        tblCart.getColumns().get(3).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/sample/Images/icons8-edit-30.png");
            ImageView delete = new ImageView("/sample/Images/icons8-delete-bin-30.png");

            edit.setOnMouseClicked(event -> {
                updateDrinkDetail medi = tblCart.getSelectionModel().getSelectedItem();

                backBtn.setDisable(true);
                txtName.setText(medi.getDrinkId());
                txtQty1.setText(String.valueOf(medi.getQty()));
                loadData();
            });

            delete.setOnMouseClicked(event -> {
                updateDrinkDetail medi = tblCart.getSelectionModel().getSelectedItem();
                backBtn.setDisable(true);
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Are you suwr?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp = result.toString();

                if (temp.equals("Optional[ButtonType [text=Yes, buttonData=OK_DONE]]")) {
                    try {
                        List<finalDrinkDetail> l = ManageDrink.getDrinkDetailId(medi.getPatId());
                        if (medi.getDrinkId() != "null") {
                            for (int j = 0; j < l.size(); j++) {
                                Drink med = ManageDrink.getDrink(l.get(j).getDrinkId());
                                int newQty = med.getQty() + l.get(j).getQty();
                                ManageDrink.updateDrinkQty(med.getId(), newQty);
                            }
                        }

                        ManageDrink.deleteDrinkDetail(medi.getDrinkId(), medi.getPatId());
                        loadData();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

            return new ReadOnlyObjectWrapper(new HBox(10, edit, delete));
        });

        tblCart.getColumns().setAll(colId, colQTY, colTotal, colDelete);

        tblCart.getItems().setAll(loadTableData());

    }

    public void addToCartOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtNewQty.getText() != null) {
            Drink medi = ManageDrink.getDrink(txtName.getText());
            int qty = medi.getQty() + Integer.parseInt(txtQty1.getText());
            pri = medi.getPrice();
            double total = Integer.parseInt(txtNewQty.getText()) * pri;
            if (qty < Integer.parseInt(txtNewQty.getText())) {
                new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
                return;
            }
            int temp1 = qty - Integer.parseInt(txtNewQty.getText());
            String n = String.valueOf(temp1);
            ManageDrink.updateDrinkDetail(temp, txtName.getText(), n, total);

            Drink med = ManageDrink.getDrink(txtName.getText());
            int newQty = 0;
            if (med != null) {
                newQty = Integer.parseInt(String.valueOf(med.getQty())) + Integer.parseInt(txtQty1.getText()) - Integer.parseInt(txtNewQty.getText());
            }
            ManageDrink.updateDrinkQty(txtName.getText(), newQty);

            loadData();
            return;
        }
        new Alert(Alert.AlertType.WARNING, "Fill Form").showAndWait();
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/updateDrinkToPatientForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) patDrink.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void BackOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/updateDrinkToPatientForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) patDrink.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public ObservableList<updateDrinkDetail> loadTableData() {
        try {

            List<updateDrinkDetail> allEq = ManageDrink.getAllDrinkDetail(temp);

            ObservableList<updateDrinkDetail> tableData = FXCollections.observableArrayList();
            for (updateDrinkDetail medi : allEq) {
                tableData.add(new updateDrinkDetail(
                        medi.getPatId(),
                        medi.getDrinkId(),
                        medi.getQty(),
                        medi.getPrice()
                ));
                medi.getDrinkId();
            }

            return tableData;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadData() {
        tblCart.getItems().setAll(loadTableData());
    }

    public void Qty(KeyEvent keyEvent) {
        String regEx = "^[1-9][0-9]{0,2}$";
        String typeText = txtNewQty.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtNewQty.getParent().setStyle("-fx-border-color: green");
            place.setDisable(false);
            addCart.setDisable(false);
        } else {
            txtNewQty.getParent().setStyle("-fx-border-color: red");
            place.setDisable(true);
            addCart.setDisable(true);
        }
    }
}
