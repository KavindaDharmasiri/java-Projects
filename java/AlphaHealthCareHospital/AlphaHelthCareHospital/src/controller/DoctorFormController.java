package controller;

import Tm.Doctor;
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

public class DoctorFormController {
    public AnchorPane doctorDashBoard;
    public TableView<Doctor> tblDoctor;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colNic;
    public TableColumn colContact;
    public TableColumn colSpecialist;
    public TableColumn colDelete;

    public void initialize() {
        tblDoctor.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblDoctor.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblDoctor.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblDoctor.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("nic"));
        tblDoctor.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblDoctor.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("specialist"));


        tblDoctor.getColumns().get(6).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/sample/Images/icons8-edit-30.png");
            ImageView delete = new ImageView("/sample/Images/icons8-delete-bin-30.png");

            edit.setOnMouseClicked(event -> {
                Doctor medi = tblDoctor.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/updateDocForm.fxml"));
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UpdateDocFormController controller = fxmlLoader.getController();
                controller.txtDocId.setText(medi.getId());
                controller.txtDocName.setText(medi.getName());
                controller.txtDocContact.setText(medi.getContact());
                controller.txtDocAddress.setText(medi.getAddress());
                controller.txtDocNic.setText(medi.getNic());
                controller.txtDocSpecialist.setText(medi.getSpecialist());

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
                loadData();
            });

            delete.setOnMouseClicked(event -> {
                Doctor medi = tblDoctor.getSelectionModel().getSelectedItem();

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Are you suwr?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp = result.toString();

                if (temp.equals("Optional[ButtonType [text=Yes, buttonData=OK_DONE]]")) {
                    try {
                        ManageDoctor.deleteDoctor(medi);
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

        tblDoctor.getColumns().setAll(colId, colName, colSpecialist, colAddress, colNic, colContact, colDelete);

        tblDoctor.getItems().setAll(loadTableData());

    }

    public void addDoctor(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/addDocForm.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
        loadData();
    }

    public ObservableList<Doctor> loadTableData() {
        try {

            List<Doctor> allEq = ManageDoctor.getAllDoctor();
            ObservableList<Doctor> tableData = FXCollections.observableArrayList();

            for (Doctor medi : allEq) {

                tableData.add(new Doctor(
                        medi.getId(),
                        medi.getName(),
                        medi.getSpecialist(),
                        medi.getAddress(),
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
        tblDoctor.getItems().setAll(loadTableData());
    }
}
