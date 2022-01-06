package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import module.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UpdateDocPatDetFormController {
    public AnchorPane updateDocPatDashBoard;
    public TextField txtPatientName;
    public JFXComboBox cmbLabId;
    public JFXComboBox cmbClothId;
    public TextField txtPatientName1;
    private String valueTwo;
    private String valueSeven;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadLabIds();
        loadClothIds();

        cmbLabId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            valueTwo = (String) newValue;
        });

        cmbClothId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            valueSeven = (String) newValue;
        });

    }

    private void loadClothIds() throws SQLException, ClassNotFoundException {
        List<String> clothIds = new ManageCloth().getAllClothIds();
        cmbClothId.getItems().addAll(clothIds);
    }

    private void loadLabIds() throws SQLException, ClassNotFoundException {
        List<String> labIds = new ManageLab().getAllLabIds();
        cmbLabId.getItems().addAll(labIds);
    }

    public void updateApoinment(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (valueTwo == null) {
            valueTwo = "null";
        }
        if (valueSeven == null) {
            valueSeven = "null";
        }

        ButtonType yes = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Cancel", ButtonBar.ButtonData.OK_DONE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                , "Success", yes, no);
        alert.setTitle("Confirmation Alert");
        Optional<ButtonType> result = alert.showAndWait();
        String temp = result.toString();

        apoinmentDetail det = ManageApoinment.getApointmentDetail(txtPatientName1.getText());
        Patient pat = ManagePatient.getPatientDet(det.getPatId());
        String room = ManageRoom.getRoomDet(det.getPatId());
        String park = hospitalDetailController.getParkDet(det.getPatId());

        int total = getPrice(txtPatientName1.getText(), valueSeven, room);

        String mediId = getMedId();
        String eqId = getEqId();
        String drinkId = getDrinkId();


        ManageDoctorPatientDetail.updateDocPatDetail(valueTwo, drinkId, valueSeven, mediId, eqId,
                txtPatientName1.getText(), total);

        back();
    }

    private String getDrinkId() throws SQLException, ClassNotFoundException {
        String mediId = null;
        List<finalDrinkDetail> me = ManageDrink.getDrinkDetailId(txtPatientName1.getText());
        for (int i = 0; i < me.size(); i++) {
            if (mediId != null) {
                mediId = mediId + " , " + me.get(i).getDrinkId();
            } else {
                mediId = me.get(i).getDrinkId();
            }
        }
        if (mediId == null) {
            mediId = "null";
        }
        return mediId;
    }

    private String getEqId() throws SQLException, ClassNotFoundException {
        String mediId = null;
        List<finalEqDetail> me = ManageEq.getEquipmentDetailId(txtPatientName1.getText());
        for (int i = 0; i < me.size(); i++) {
            if (mediId != null) {
                mediId = mediId + " , " + me.get(i).getEqId();
            } else {
                mediId = me.get(i).getEqId();
            }
        }
        if (mediId == null) {
            mediId = "null";
        }
        return mediId;
    }

    public String getMedId() throws SQLException, ClassNotFoundException {
        String mediId = null;
        List<finalMedicalDetail> me = ManageMedicine.getMedicalDetailId(txtPatientName1.getText());
        for (int i = 0; i < me.size(); i++) {
            if (mediId != null) {
                mediId = mediId + " , " + me.get(i).getMediId();
            } else {
                mediId = me.get(i).getMediId();
            }
        }
        if (mediId == null) {
            mediId = "null";
        }
        return mediId;
    }

    private int getPrice(String valueOne, String cloth, String room) throws SQLException, ClassNotFoundException {
        int medi = 0;
        List<finalMedicalDetail> me = ManageMedicine.getMedicalDetailId(valueOne);
        for (int i = 0; i < me.size(); i++) {
            medi += me.get(i).getPrice();
        }

        List<finalDrinkDetail> dr = ManageDrink.getDrinkDetailId(valueOne);
        for (int i = 0; i < dr.size(); i++) {
            medi += dr.get(i).getPrice();
        }

        List<finalEqDetail> eq = ManageEq.getEquipmentDetailId(valueOne);
        for (int i = 0; i < eq.size(); i++) {
            medi += eq.get(i).getPrice();
        }

        int clot = ManageCloth.getClothPrice(cloth);
        int roo = ManageRoom.getRomPrice(room);
        int total = medi + clot + roo;
        return total;
    }

    private void back() throws IOException {
        URL resource = getClass().getResource("../view/updateDocPatDetForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) updateDocPatDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void backToApoinmentList(ActionEvent actionEvent) throws IOException {
        back();
    }

    public void updateDrink(ActionEvent actionEvent) {
        UpdateDrinkToPatientFormController.set(txtPatientName1.getText());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/updateDrinkToPatientForm.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UpdateDrinkToPatientFormController controller = fxmlLoader.getController();
        controller.lblPatId.setText(txtPatientName1.getText());
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public void updatemedicine(ActionEvent actionEvent) {
        UpdateMedicineToPatientFormController.set(txtPatientName1.getText());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/updateMedicineToPatientForm.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UpdateMedicineToPatientFormController controller = fxmlLoader.getController();
        controller.lblPatId.setText(txtPatientName1.getText());
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public void updateEq(ActionEvent actionEvent) {
        UpdateEqToPatientFormController.set(txtPatientName1.getText());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/updateEqToPatientForm.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UpdateEqToPatientFormController controller = fxmlLoader.getController();
        controller.lblPatId.setText(txtPatientName1.getText());
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }
}
