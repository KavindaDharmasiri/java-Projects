package controller;

import Tm.DocPatDetail;
import Tm.Drink;
import Tm.Equpment;
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
import module.finalDrinkDetail;
import module.finalEqDetail;
import module.finalMedicalDetail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AddPationDetailFormController {
    public AnchorPane addPatDetDashBoard;
    public TableView<DocPatDetail> tblDocPatientDet;
    public TableColumn colPatId;
    public TableColumn colName;
    public TableColumn colLabId;
    public TableColumn colFoodId;
    public TableColumn colDrinkId;
    public TableColumn colClothId;
    public TableColumn colMedicineId;
    public TableColumn colEqupmentId;
    public TableColumn colApointmentId;
    public TableColumn coledit;


    public void initialize() {
        tblDocPatientDet.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("apoinmentId"));
        tblDocPatientDet.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("patid"));
        tblDocPatientDet.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("patName"));
        tblDocPatientDet.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("labId"));
        tblDocPatientDet.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("drinkId"));
        tblDocPatientDet.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("clothId"));
        tblDocPatientDet.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("mediId"));
        tblDocPatientDet.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("eqId"));


        tblDocPatientDet.getColumns().get(8).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/sample/Images/draw.png");
            ImageView delete = new ImageView("/sample/Images/trash.png");

            edit.setOnMouseClicked(event -> {
                DocPatDetail medi = tblDocPatientDet.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/updateDocPatDetForm.fxml"));
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UpdateDocPatDetFormController controller = fxmlLoader.getController();
                controller.txtPatientName1.setText(medi.getPatid());
                controller.txtPatientName.setText(medi.getPatName());

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
                loadData();
            });

            delete.setOnMouseClicked(event -> {
                DocPatDetail medi = tblDocPatientDet.getSelectionModel().getSelectedItem();

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Are you suwr?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp = result.toString();

                if (temp.equals("Optional[ButtonType [text=Yes, buttonData=OK_DONE]]")) {
                    try {
                        List<finalMedicalDetail> i = ManageMedicine.getMedicalDetailId(medi.getPatid());
                        if (medi.getMediId() != "null") {
                            for (int j = 0; j < i.size(); j++) {
                                Medicine med = ManageMedicine.getMedicine(i.get(j).getMediId());
                                int newQty = Integer.parseInt(med.getQty()) + i.get(j).getQty();
                                ManageMedicine.updateMedicineQty(med.getId(), newQty);
                            }
                        }
                        List<finalEqDetail> k = ManageEq.getEquipmentDetailId(medi.getPatid());
                        if (medi.getEqId() != "null") {
                            for (int j = 0; j < k.size(); j++) {
                                Equpment med = ManageEq.getEquipment(k.get(j).getEqId());
                                int newQty = med.getQty() + k.get(j).getQty();
                                ManageEq.updateEqQty(med.getId(), newQty);
                            }
                        }

                        List<finalDrinkDetail> l = ManageDrink.getDrinkDetailId(medi.getPatid());
                        if (medi.getDrinkId() != "null") {
                            for (int j = 0; j < l.size(); j++) {
                                Drink med = ManageDrink.getDrink(l.get(j).getDrinkId());
                                int newQty = med.getQty() + l.get(j).getQty();
                                ManageDrink.updateDrinkQty(med.getId(), newQty);
                            }
                        }
                        ManageDoctorPatientDetail.deleteDocPatDetail(medi);
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

        tblDocPatientDet.getColumns().setAll(colApointmentId, colPatId, colName, colLabId, colDrinkId, colClothId, colMedicineId, colEqupmentId, coledit);

        tblDocPatientDet.getItems().setAll(loadTableData());

    }

    public void addDocPatientDet(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/addDocPatDetForm.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
        loadData();
    }

    public ObservableList<DocPatDetail> loadTableData() {
        try {

            List<DocPatDetail> allEq = ManageDoctorPatientDetail.getAllDocpatDetail();
            ObservableList<DocPatDetail> tableData = FXCollections.observableArrayList();

            for (DocPatDetail medi : allEq) {

                tableData.add(new DocPatDetail(
                        medi.getApoinmentId(),
                        medi.getPatid(),
                        medi.getPatName(),
                        medi.getLabId(),
                        medi.getDrinkId(),
                        medi.getClothId(),
                        medi.getMediId(),
                        medi.getEqId()

                ));
            }

            return tableData;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadData() {
        tblDocPatientDet.getItems().setAll(loadTableData());
    }
}
