package controller;

import Tm.Medicine;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.loadEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MedicalFormController implements loadEvent {
    public AnchorPane medicineDashBoard;
    public TableView<Medicine> tblMedicine;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAvailability;
    public TableColumn colDelete;
    public TableColumn colPrice;
    public TableColumn colQty;

    public void initialize() {
        tblMedicine.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblMedicine.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblMedicine.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("availability"));
        tblMedicine.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("Qty"));
        tblMedicine.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("price"));

        tblMedicine.getColumns().get(5).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/sample/Images/icons8-edit-30.png");
            ImageView delete = new ImageView("/sample/Images/icons8-delete-bin-30.png");

            edit.setOnMouseClicked(event -> {
                Medicine medi = tblMedicine.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/updateMedicineForm.fxml"));
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UpdateMedicineFormController controller = fxmlLoader.getController();
                controller.txtMedicineId.setText(medi.getId());
                controller.txtMedicineName.setText(medi.getName());
                controller.txtMedicinePrice.setText(medi.getPrice());

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
                loadData();
            });

            delete.setOnMouseClicked(event -> {
                Medicine medi = tblMedicine.getSelectionModel().getSelectedItem();

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Are you suwr?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp = result.toString();

                if (temp.equals("Optional[ButtonType [text=Yes, buttonData=OK_DONE]]")) {
                    try {
                        ManageMedicine.deleteMedicine(medi);
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

        tblMedicine.getColumns().setAll(colId, colName, colAvailability, colQty, colPrice, colDelete);

        tblMedicine.getItems().setAll(loadTableData());

    }


    public ObservableList<Medicine> loadTableData() {
        try {

            List<Medicine> allCustomers = ManageMedicine.getAllMedicine();
            ObservableList<Medicine> tableData = FXCollections.observableArrayList();
            for (Medicine medi : allCustomers) {
                tableData.add(new Medicine(
                        medi.getId(),
                        medi.getName(),
                        medi.getAvailability(),
                        String.valueOf(medi.getQty()),
                        String.valueOf(medi.getPrice())
                ));
            }

            return tableData;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addMedicine(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/addMedicineForm.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
        loadData();
    }

    @Override
    public void loadData() {
        tblMedicine.getItems().setAll(loadTableData());
    }
}
