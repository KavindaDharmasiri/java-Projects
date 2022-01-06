package controller;

import Tm.PatientTm;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PatientFormController {
    public AnchorPane patientDashBoard;
    public TableView<PatientTm> tblPatient;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colNic;
    public TableColumn colContact;
    public TableColumn colAge;
    public TableColumn colDelete;

    public void initialize() {
        tblPatient.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblPatient.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblPatient.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblPatient.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("nic"));
        tblPatient.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblPatient.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("age"));

        tblPatient.getColumns().get(6).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/sample/Images/icons8-edit-30.png");
            ImageView delete = new ImageView("/sample/Images/icons8-delete-bin-30.png");

            edit.setOnMouseClicked(event -> {
                PatientTm medi = tblPatient.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/updatePatientForm.fxml"));
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UpdatePatientFormController controller = fxmlLoader.getController();
                controller.txtPatientId.setText(medi.getId());
                controller.txtPatientName.setText(medi.getName());
                controller.txtPatientAddress.setText(medi.getAddress());
                controller.txtPatientAge.setText(String.valueOf(medi.getAge()));
                controller.txtPatientNic.setText(medi.getNic());
                controller.txtPatientContact.setText(medi.getContact());

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
                loadData();
            });

            delete.setOnMouseClicked(event -> {
                PatientTm medi = tblPatient.getSelectionModel().getSelectedItem();

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Are you suwr?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp = result.toString();

                if (temp.equals("Optional[ButtonType [text=Yes, buttonData=OK_DONE]]")) {
                    try {
                        ManagePatient.deletePatient(medi);
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

        tblPatient.getColumns().setAll(colId, colName, colAddress, colAge, colNic, colContact, colDelete);

        tblPatient.getItems().setAll(loadTableData());

    }

    public void addPatient(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/addPatientForm.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
        loadData();
    }

    public ObservableList<PatientTm> loadTableData() {
        try {

            List<PatientTm> allEq = ManagePatient.getAllPatient();
            ObservableList<PatientTm> tableData = FXCollections.observableArrayList();
            for (PatientTm medi : allEq) {
                tableData.add(new PatientTm(
                        medi.getId(),
                        medi.getName(),
                        medi.getAddress(),
                        medi.getAge(),
                        medi.getNic(),
                        medi.getContact()
                ));
            }

            return tableData;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadData() {
        tblPatient.getItems().setAll(loadTableData());
    }
}
