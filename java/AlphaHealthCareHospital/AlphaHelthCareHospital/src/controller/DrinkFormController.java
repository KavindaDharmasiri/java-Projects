package controller;

import Tm.Drink;
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

public class DrinkFormController {
    public AnchorPane drinkDashBoard;
    public TableView<Drink> tblDrink;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAvailability;
    public TableColumn colDelete;
    public TableColumn colPrice;
    public TableColumn colQty;

    public void initialize() {
        tblDrink.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblDrink.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblDrink.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("availability"));
        tblDrink.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblDrink.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("price"));

        tblDrink.getColumns().get(5).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/sample/Images/icons8-edit-30.png");
            ImageView delete = new ImageView("/sample/Images/icons8-delete-bin-30.png");

            edit.setOnMouseClicked(event -> {
                Drink medi = tblDrink.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/updateDrinkForm.fxml"));
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UpdateDrinkFormController controller = fxmlLoader.getController();
                controller.txtDrinkId.setText(medi.getId());
                controller.txtDrinkName.setText(medi.getName());
                controller.txtDrinkPrice.setText(String.valueOf(medi.getPrice()));

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
                loadData();
            });

            delete.setOnMouseClicked(event -> {
                Drink medi = tblDrink.getSelectionModel().getSelectedItem();

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Are you suwr?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp = result.toString();

                if (temp.equals("Optional[ButtonType [text=Yes, buttonData=OK_DONE]]")) {
                    try {
                        ManageDrink.deleteDrink(medi);
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

        tblDrink.getColumns().setAll(colId, colName, colAvailability, colQty, colPrice, colDelete);

        tblDrink.getItems().setAll(loadTableData());

    }

    public void addDrink(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/addDrinkForm.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
        loadData();
    }

    public ObservableList<Drink> loadTableData() {
        try {

            List<Drink> allEq = ManageDrink.getAllDrink();
            ObservableList<Drink> tableData = FXCollections.observableArrayList();
            for (Drink medi : allEq) {
                tableData.add(new Drink(
                        medi.getId(),
                        medi.getName(),
                        medi.getAvailability(),
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
        tblDrink.getItems().setAll(loadTableData());
    }
}
