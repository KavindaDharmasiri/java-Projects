package controller;

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
import module.apoinmentDetail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ApoinmentFormController {
    public AnchorPane apoinmentDashBoard;
    public TableView<apoinmentDetail> tblApoinment;
    public TableColumn colPatId;
    public TableColumn colDocId;
    public TableColumn colNurseId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colDelete;
    public TableColumn colApoinmentId;

    public void initialize() {
        tblApoinment.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("apoiId"));
        tblApoinment.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("patId"));
        tblApoinment.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("docId"));
        tblApoinment.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("nurseId"));
        tblApoinment.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblApoinment.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("time"));

        tblApoinment.getColumns().get(6).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/sample/Images/icons8-edit-30.png");
            ImageView delete = new ImageView("/sample/Images/icons8-delete-bin-30.png");

            edit.setOnMouseClicked(event -> {
                apoinmentDetail medi = tblApoinment.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/updateApoinmentForm.fxml"));
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UpdateApoinmentFormController controller = fxmlLoader.getController();
                controller.txtApoinmentDate.setText(medi.getDate());
                controller.txtApoinmentTime.setText(medi.getTime());
                controller.lblApoinmentId.setText(medi.getApoiId());
                controller.lblDoctorId.setText(medi.getDocId());
                controller.lblNurseId.setText(medi.getNurseId());
                controller.lblPatientId.setText(medi.getPatId());

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
                loadData();
            });

            delete.setOnMouseClicked(event -> {
                apoinmentDetail medi = tblApoinment.getSelectionModel().getSelectedItem();

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Are you suwr?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp = result.toString();

                if (temp.equals("Optional[ButtonType [text=Yes, buttonData=OK_DONE]]")) {
                    try {
                        ManageApoinment.deleteApoinment(medi);
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

        tblApoinment.getColumns().setAll(colApoinmentId, colPatId, colDocId, colNurseId, colDate, colTime, colDelete);

        tblApoinment.getItems().setAll(loadTableData());

    }

    public void addApoinment(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/addApoinmentForm.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
        loadData();
    }

    public ObservableList<apoinmentDetail> loadTableData() {
        try {

            List<apoinmentDetail> allEq = ManageApoinment.getAllApoinment();
            ObservableList<apoinmentDetail> tableData = FXCollections.observableArrayList();
            for (apoinmentDetail medi : allEq) {
                tableData.add(new apoinmentDetail(
                        medi.getApoiId(),
                        medi.getPatId(),
                        medi.getDocId(),
                        medi.getNurseId(),
                        medi.getTime(),
                        medi.getDate()
                ));
            }

            return tableData;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadData() {
        tblApoinment.getItems().setAll(loadTableData());
    }
}
