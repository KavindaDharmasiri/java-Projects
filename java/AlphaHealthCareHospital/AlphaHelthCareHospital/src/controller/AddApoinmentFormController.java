package controller;

import DBConnection.DBConnection;
import Tm.Doctor;
import Tm.Nurse;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import module.Patient;
import module.apoinmentDetail;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class AddApoinmentFormController {
    public AnchorPane addApoinmentDashBoard;
    public JFXComboBox cmbPatientId;
    public TextField txtApoinmentDate;
    public JFXComboBox cmbDoctorId;
    public JFXComboBox cmbNurseId;
    public TextField txtApoinmentTime;
    public Label lblApoinmentId;
    public JFXButton addBtn;
    String valueOne;
    String valueTwo;
    ArrayList<String> nam = new ArrayList<>();
    int d = 0;
    int t = 0;
    private String valueThree;
    private int totl;

    public void initialize() throws SQLException, ClassNotFoundException {
        addBtn.setDisable(true);
        List<apoinmentDetail> apoi = ManageApoinment.getAllApoinment();
        lblApoinmentId.setText(ManageApoinment.getApoiId());

        loadPatientIds(apoi);
        loadDocIds(apoi);
        loadNurseIds(apoi);

        cmbPatientId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            valueOne = (String) newValue;
            setbtn(false);
        });

        cmbDoctorId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            valueTwo = (String) newValue;
            setbtn(false);
        });

        cmbNurseId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            valueThree = (String) newValue;
            setbtn(false);
        });

    }

    private void loadPatientIds(List<apoinmentDetail> apoi) throws SQLException, ClassNotFoundException {
        List<String> patIds = new ManagePatient().getAllPatientIds();
        for (int i = 0; i < patIds.size(); i++) {
            totl = 0;
            for (int j = 0; j < apoi.size(); j++) {
                if (patIds.get(i).equals(apoi.get(j).getPatId())) {
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

    private void loadDocIds(List<apoinmentDetail> apoi) throws SQLException, ClassNotFoundException {
        List<String> docIds = new ManageDoctor().getAllDoctorIds();
        cmbDoctorId.getItems().addAll(docIds);
    }

    private void loadNurseIds(List<apoinmentDetail> apoi) throws SQLException, ClassNotFoundException {
        List<String> nurseIds = new ManageNurse().getAllNurseIds();
        cmbNurseId.getItems().addAll(nurseIds);
    }

    public void addApoinment(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if (txtApoinmentTime.getText() != null && txtApoinmentDate.getText() != null) {
            ManageApoinment.addNewApoinment(lblApoinmentId.getText(), valueOne, valueTwo, valueThree, txtApoinmentTime, txtApoinmentDate);

            ButtonType PrintReceipt = new ButtonType("Print Receipt", ButtonBar.ButtonData.OK_DONE);
            ButtonType Cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                    , "Success", PrintReceipt, Cancel);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();
            String temp = result.toString();

            if (temp.equals("Optional[ButtonType [text=Print Receipt, buttonData=OK_DONE]]")) {
                printReceipt();
            } else {
                return;
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Fill All.").show();
        }
        notification();
        back();
    }

    private void notification() {
        AudioClip clip = Applet.newAudioClip(getClass().getResource("/sample/audio/success-notification-alert_A_major.wav"));

        Image image = new Image("/sample/Images/icons8-ok-30.png");
        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text("Appointment Adding Success..");
        notifications.title("Success Massage");
        notifications.hideAfter(Duration.seconds(20));
        notifications.darkStyle();
        notifications.position(Pos.BASELINE_RIGHT);
        notifications.show();

        clip.play();
    }

    public void printReceipt() throws SQLException, ClassNotFoundException {
        hospitalDetailController.deleteFromApointment();
        String patName = getpatientName(valueOne);
        String docName = getDocName(valueTwo);
        String nurseName = getNurseName(valueThree);
        hospitalDetailController.addApoinmentDetailForReport(lblApoinmentId.getText(), patName, docName, nurseName, txtApoinmentTime.getText(), txtApoinmentDate.getText());

        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/module/report/Apointment.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getpatientName(String valueOne) throws SQLException, ClassNotFoundException {
        Patient pat = ManagePatient.getPatientDet(valueOne);
        return pat.getName();
    }

    private String getNurseName(String valueThree) throws SQLException, ClassNotFoundException {
        Nurse nurse = ManageNurse.getNurseDetail(valueThree);
        return nurse.getName();
    }

    private String getDocName(String valueTwo) throws SQLException, ClassNotFoundException {
        Doctor doc = ManageDoctor.getDocDetail(valueTwo);
        return doc.getName();
    }

    public void backToApoinmentList(ActionEvent actionEvent) throws IOException {
        back();
    }

    public void back() throws IOException {
        URL resource = getClass().getResource("../view/addApoinmentForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) addApoinmentDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void date(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](20|22)\\d\\d$";

        String typeText = txtApoinmentDate.getText();
        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            d = 1;
            txtApoinmentDate.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            d = 0;
            txtApoinmentDate.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                txtApoinmentTime.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        addBtn.setDisable(true);
        addBtn.setDisable(d != 1 || t != 1 || txtApoinmentTime.getText() == null || txtApoinmentDate.getText() == null || valueOne == null || valueTwo == null || valueThree == null);

    }

    public void time(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^(0?[1-9]|1[0-2]):[0-5][0-9]$";
        String typeText = txtApoinmentTime.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            t = 1;
            txtApoinmentTime.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            t = 0;
            txtApoinmentTime.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }
}
