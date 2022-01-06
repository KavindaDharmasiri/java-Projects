package controller;

import Tm.Room;
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

public class RoomFormController {
    public AnchorPane RoomDashBoard;
    public TableView<Room> tblRoom;
    public TableColumn colRoomId;
    public TableColumn colRoomType;
    public TableColumn colAvailability;
    public TableColumn colDelete;
    public TableColumn colPrice;

    public void initialize() {
        tblRoom.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblRoom.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("type"));
        tblRoom.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("availability"));
        tblRoom.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("price"));

        tblRoom.getColumns().get(4).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/sample/Images/icons8-edit-30.png");
            ImageView delete = new ImageView("/sample/Images/icons8-delete-bin-30.png");

            edit.setOnMouseClicked(event -> {
                Room medi = tblRoom.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/updateRoomForm.fxml"));
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UpdateRoomFormController controller = fxmlLoader.getController();
                controller.txtRoomId.setText(medi.getId());
                controller.txtRoomType.setText(medi.getType());
                controller.txtRoomPrice.setText(String.valueOf(medi.getPrice()));

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
                loadData();
            });

            delete.setOnMouseClicked(event -> {
                Room medi = tblRoom.getSelectionModel().getSelectedItem();

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Are you suwr?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp = result.toString();

                if (temp.equals("Optional[ButtonType [text=Yes, buttonData=OK_DONE]]")) {
                    try {
                        ManageRoom.deleteRoom(medi);
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

        tblRoom.getColumns().setAll(colRoomId, colRoomType, colAvailability, colPrice, colDelete);

        tblRoom.getItems().setAll(loadTableData());

    }

    public void addRoom(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/addRoomForm.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
        loadData();
    }

    public ObservableList<Room> loadTableData() {
        try {

            List<Room> allEq = ManageRoom.getAllRoom();
            ObservableList<Room> tableData = FXCollections.observableArrayList();
            for (Room medi : allEq) {
                tableData.add(new Room(
                        medi.getId(),
                        medi.getType(),
                        medi.getAvailability(),
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
        tblRoom.getItems().setAll(loadTableData());
    }
}
