package controller;

import Tm.Lab;
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

public class LabFormController {
    public AnchorPane LabDashBoard;
    public TableColumn colLabId;
    public TableColumn colLabType;
    public TableColumn colAvailability;
    public TableColumn colDelete;
    public TableView<Lab> tblLab;

    public void initialize() {
        tblLab.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblLab.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("type"));
        tblLab.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("availability"));

        tblLab.getColumns().get(3).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/sample/Images/icons8-edit-30.png");
            ImageView delete = new ImageView("/sample/Images/icons8-delete-bin-30.png");

            edit.setOnMouseClicked(event -> {
                Lab medi = tblLab.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/updateLabForm.fxml"));
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UpdateLabFormController controller = fxmlLoader.getController();
                controller.txtLabId.setText(medi.getId());
                controller.txtLabType.setText(medi.getType());

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
                loadData();
            });

            delete.setOnMouseClicked(event -> {
                Lab medi = tblLab.getSelectionModel().getSelectedItem();

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Are you suwr?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp = result.toString();

                if (temp.equals("Optional[ButtonType [text=Yes, buttonData=OK_DONE]]")) {
                    try {
                        ManageLab.deleteLab(medi);
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

        tblLab.getColumns().setAll(colLabId, colLabType, colAvailability, colDelete);

        tblLab.getItems().setAll(loadTableData());

    }

    public void addLab(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/addLabForm.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
        loadData();
    }

    public ObservableList<Lab> loadTableData() {
        try {

            List<Lab> allEq = ManageLab.getAllLab();
            ObservableList<Lab> tableData = FXCollections.observableArrayList();
            for (Lab medi : allEq) {
                tableData.add(new Lab(
                        medi.getId(),
                        medi.getType(),
                        medi.getAvailability()
                ));
            }

            return tableData;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadData() {
        tblLab.getItems().setAll(loadTableData());
    }
}
