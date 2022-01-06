package controller;

import Tm.Nurse;
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

public class NurseFormController {
    public TableView<Nurse> tblNurse;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colNic;
    public TableColumn colContact;
    public TableColumn colDelete;
    public AnchorPane nurseDashBoard;

    public void initialize() {
        tblNurse.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblNurse.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblNurse.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblNurse.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("nic"));
        tblNurse.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("contact"));

        tblNurse.getColumns().get(5).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/sample/Images/icons8-edit-30.png");
            ImageView delete = new ImageView("/sample/Images/icons8-delete-bin-30.png");

            edit.setOnMouseClicked(event -> {
                Nurse medi = tblNurse.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/updateNurseForm.fxml"));
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UpdateNurseFormController controller = fxmlLoader.getController();
                controller.txtNurseId.setText(medi.getId());
                controller.txtNurseName.setText(medi.getName());
                controller.txtNurseContact.setText(medi.getContact());
                controller.txtNurseAddress.setText(medi.getAddress());
                controller.txtNurseNic.setText(medi.getNic());

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
                loadData();
            });

            delete.setOnMouseClicked(event -> {
                Nurse medi = tblNurse.getSelectionModel().getSelectedItem();

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Are you suwr?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp = result.toString();

                if (temp.equals("Optional[ButtonType [text=Yes, buttonData=OK_DONE]]")) {
                    try {
                        ManageNurse.deleteNurse(medi);
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

        tblNurse.getColumns().setAll(colId, colName, colAddress, colNic, colContact, colDelete);

        tblNurse.getItems().setAll(loadTableData());

    }

    public void addNurse(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/addNurseForm.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
        loadData();
    }

    public ObservableList<Nurse> loadTableData() {
        try {

            List<Nurse> allEq = ManageNurse.getAllNurse();
            ObservableList<Nurse> tableData = FXCollections.observableArrayList();
            for (Nurse medi : allEq) {
                tableData.add(new Nurse(
                        medi.getId(),
                        medi.getName(),
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
        tblNurse.getItems().setAll(loadTableData());
    }
}
