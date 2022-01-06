package controller;

import Tm.DocPatDetail;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import module.*;
import org.controlsfx.control.Notifications;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddDocPatDetFormController {
    public AnchorPane addDocPatDashBoard;
    public JFXComboBox cmbPatientId;
    public TextField txtPatientName;
    public JFXComboBox cmbLabId;
    public JFXComboBox cmbClothId;
    ArrayList<String> nam = new ArrayList<>();
    private String valueOne;
    private String valueSeven;
    private String valueTwo;
    private String tempName;
    private String valueSix;
    private int totl = 0;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadPatientApoiIds();
        loadLabIds();
        loadClothIds();

        cmbPatientId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            valueOne = (String) newValue;
            try {
                tempName = ManagePatient.getPatientName(valueOne).getName();
                txtPatientName.setText(tempName);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

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

    private void loadPatientApoiIds() throws SQLException, ClassNotFoundException {
        List<String> patIds = new ManageApoinment().getAllPatientApoiIds();
        List<DocPatDetail> doc = ManageDoctorPatientDetail.getAllDocpatDetail();

        for (int i = 0; i < patIds.size(); i++) {
            totl = 0;
            for (int j = 0; j < doc.size(); j++) {
                if (patIds.get(i).equals(doc.get(j).getPatid())) {
                    totl = 1;
                }
            }
            if (totl == 0) {
                nam.add(patIds.get(i));
            }
        }
        if (nam.size() != 0) {
            cmbPatientId.getItems().addAll(nam);
        }
    }

    public void addApoinment(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (valueTwo == null) {
            valueTwo = "null";
        }
        if (valueSix == null) {
            valueSix = "null";
        }
        if (valueSeven == null) {
            valueSeven = "null";
        }
        if (valueOne != null) {

            apoinmentDetail det = ManageApoinment.getApointmentDetail(valueOne);
            Patient pat = ManagePatient.getPatientDet(det.getPatId());
            String room = ManageRoom.getRoomDet(det.getPatId());
            String park = hospitalDetailController.getParkDet(det.getPatId());

            int total = getPrice(valueOne, valueSeven, room);
            String mediId = getMedId();
            String eqId = getEqId();
            String drinkId = getDrinkId();

            ManageApoinment.addNewApoinmentDetail(det.getApoiId(), valueOne, tempName, valueTwo,
                    drinkId, valueSeven, mediId, eqId, det.getPatId(), pat.getName(), det.getDocId(),
                    det.getNurseId(), det.getDate(), det.getTime(), room, park, total, "No");

            notification();
            back();
        }
    }

    private void notification() {
        AudioClip clip = Applet.newAudioClip(getClass().getResource("/sample/audio/success-notification-alert_A_major.wav"));

        Image image = new Image("/sample/Images/icons8-ok-30.png");
        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text("Patient Medical Detail Adding Success..");
        notifications.title("Success Massage");
        notifications.hideAfter(Duration.seconds(20));
        notifications.darkStyle();
        notifications.position(Pos.BASELINE_RIGHT);
        notifications.show();

        clip.play();
    }

    private String getDrinkId() throws SQLException, ClassNotFoundException {
        String mediId = null;
        List<finalDrinkDetail> me = ManageDrink.getDrinkDetailId(valueOne);
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
        List<finalEqDetail> me = ManageEq.getEquipmentDetailId(valueOne);
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
        List<finalMedicalDetail> me = ManageMedicine.getMedicalDetailId(valueOne);
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

    private void back() throws IOException {
        URL resource = getClass().getResource("../view/addDocPatDetForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) addDocPatDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
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

    public void backToApoinmentList(ActionEvent actionEvent) throws IOException {
        back();
    }

    public void addMedicine(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddMedicineToPatient.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddMedicineToPatientController controller = fxmlLoader.getController();
        controller.lblPatId.setText(valueOne);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public void addEq(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddEquipmentToPatient.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddEquipmentToPatientController controller = fxmlLoader.getController();
        controller.lblPatId.setText(valueOne);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public void addDrink(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddDrinkToPatient.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddDrinkToPatientController controller = fxmlLoader.getController();
        controller.lblPatId.setText(valueOne);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }
}
