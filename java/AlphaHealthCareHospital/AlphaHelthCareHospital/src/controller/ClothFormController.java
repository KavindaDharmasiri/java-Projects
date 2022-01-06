package controller;

import Tm.Cloth;
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

public class ClothFormController {
    public AnchorPane clothDashBoard;
    public TableView<Cloth> tblCloth;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAvailability;
    public TableColumn colDelete;
    public TableColumn colPrice;

    public void initialize(){
        tblCloth.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCloth.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCloth.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("availability"));
        tblCloth.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("price"));

        tblCloth.getColumns().get(4).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/sample/Images/icons8-edit-30.png");
            ImageView delete = new ImageView("/sample/Images/icons8-delete-bin-30.png");

            edit.setOnMouseClicked(event -> {
                Cloth medi = tblCloth.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/updateClothForm.fxml"));
                Parent root1 = null;
                try {
                    root1 = (Parent) fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UpdateClothFormController controller = fxmlLoader.<UpdateClothFormController>getController();
                controller.txtClothId.setText(medi.getId());
                controller.txtClothName.setText(medi.getName());
                controller.txtClothPrice.setText(String.valueOf(medi.getPrice()));

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
                loadData();
            });

            delete.setOnMouseClicked(event -> {
                Cloth medi = tblCloth.getSelectionModel().getSelectedItem();

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Are you suwr?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp=result.toString();

                if(temp.equals("Optional[ButtonType [text=Yes, buttonData=OK_DONE]]")) {
                    try {
                        ManageCloth.deleteCloth(medi);
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

        tblCloth.getColumns().setAll(colId, colName, colAvailability, colPrice,colDelete);

        tblCloth.getItems().setAll(loadTableData());

    }


    public void addCloth(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/addClothForm.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
        loadData();
    }

    public ObservableList<Cloth> loadTableData() {
        try {

            List<Cloth> allEq = ManageCloth.getAllCloth();
            ObservableList<Cloth> tableData = FXCollections.observableArrayList();
            for (Cloth medi : allEq) {
                tableData.add(new Cloth(
                        medi.getId(),
                        medi.getName(),
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
        tblCloth.getItems().setAll(loadTableData());
    }
}
