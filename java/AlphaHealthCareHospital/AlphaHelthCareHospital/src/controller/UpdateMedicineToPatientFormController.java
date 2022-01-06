package controller;

import Tm.Medicine;
import Tm.updateMedicineForm;
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
import module.finalMedicalDetail;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class UpdateMedicineToPatientFormController {
    private static String temp;
    public AnchorPane patDrink;
    public Label lblPatId;
    public TextField txtName;
    public TextField txtNewQty;
    public TableView<updateMedicineForm> tblCart;
    public TableColumn colId;
    public TableColumn colQTY;
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
        tblCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("mediId"));
        tblCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));

        tblCart.getColumns().get(3).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/sample/Images/icons8-edit-30.png");
            ImageView delete = new ImageView("/sample/Images/icons8-delete-bin-30.png");

            edit.setOnMouseClicked(event -> {
                updateMedicineForm medi = tblCart.getSelectionModel().getSelectedItem();

                backBtn.setDisable(true);
                txtName.setText(medi.getMediId());
                txtQty1.setText(String.valueOf(medi.getQty()));
                loadData();
            });

            delete.setOnMouseClicked(event -> {
                updateMedicineForm medi = tblCart.getSelectionModel().getSelectedItem();
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
                        List<finalMedicalDetail> i = ManageMedicine.getMedicalDetailId(medi.getPatId());
                        if (medi.getMediId() != "null") {
                            for (int j = 0; j < i.size(); j++) {
                                Medicine med = ManageMedicine.getMedicine(i.get(j).getMediId());
                                int newQty = Integer.parseInt(med.getQty()) + i.get(j).getQty();
                                ManageMedicine.updateMedicineQty(med.getId(), newQty);
                            }
                        }

                        ManageMedicine.deleteMediDetail(medi.getMediId(), medi.getPatId());
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
            Medicine medi = ManageMedicine.getMedicine(txtName.getText());
            int qty = Integer.parseInt(medi.getQty()) + Integer.parseInt(txtQty1.getText());
            pri = Double.parseDouble(medi.getPrice());
            double total = Integer.parseInt(txtNewQty.getText()) * pri;
            if (qty < Integer.parseInt(txtNewQty.getText())) {
                new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
                return;
            }
            ManageMedicine.updateMediDetail(temp, txtName.getText(), txtNewQty.getText(), total);


            Medicine med = ManageMedicine.getMedicine(txtName.getText());
            int newQty = 0;
            if (med != null) {
                newQty = Integer.parseInt(String.valueOf(med.getQty())) + Integer.parseInt(txtQty1.getText()) - Integer.parseInt(txtNewQty.getText());
            }
            ManageMedicine.updateMedicineQty(txtName.getText(), newQty);

            loadData();
            return;
        }
        new Alert(Alert.AlertType.WARNING, "Fill Form").showAndWait();
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/updateMedicineToPatientForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) patDrink.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void BackOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/updateMedicineToPatientForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) patDrink.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public Collection<? extends updateMedicineForm> loadTableData() {
        try {

            List<updateMedicineForm> allEq = ManageMedicine.getAllMediDetail(temp);

            ObservableList<updateMedicineForm> tableData = FXCollections.observableArrayList();
            for (updateMedicineForm medi : allEq) {
                tableData.add(new updateMedicineForm(
                        medi.getPatId(),
                        medi.getMediId(),
                        medi.getQty(),
                        medi.getPrice()
                ));
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
