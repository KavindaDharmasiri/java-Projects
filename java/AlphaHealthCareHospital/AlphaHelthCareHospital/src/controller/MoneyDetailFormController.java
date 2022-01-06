package controller;

import DBConnection.DBConnection;
import Tm.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import module.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MoneyDetailFormController {
    public AnchorPane placeMoney;
    public JFXComboBox cmbPatientId;
    public Label lblPrice;
    public JFXButton makeBtn;
    private String value;

    public void initialize() throws SQLException, ClassNotFoundException {
        makeBtn.setDisable(true);
        loadPatientApoiIds();

        cmbPatientId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            value = (String) newValue;

            try {
                int medi = getMediPrice();
                int eq = getEqPriced();
                int dr = getDrinkPriced();
                String det = ManageRoom.getRoomDet(value);
                int i = ManageRoom.getRomPrice(det);
                docPatApoinmentDetail id = ManageDoctorPatientDetail.getFinal(value);
                int x = ManageCloth.getClothPrice(id.getCloth());

                lblPrice.setText(medi + eq + dr + i + x + "/=");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            makeBtn.setDisable(false);
        });

    }

    private void loadPatientApoiIds() throws SQLException, ClassNotFoundException {
        List<String> patIds = new hospitalDetailController().getAllPatientFinalApoiIds();
        cmbPatientId.getItems().addAll(patIds);
    }

    public void makePayment(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        docPatApoinmentDetail det = ManageDoctorPatientDetail.getFinal(value);
        if (det.getSuccess().equals("No")) {

            hospitalDetailController.updateFinalDetail("Success", value);

            ButtonType PrintReceipt = new ButtonType("Print Receipt", ButtonBar.ButtonData.OK_DONE);
            ButtonType Cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                    , "Success", PrintReceipt, Cancel);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();
            String temp = result.toString();

            if (temp.equals("Optional[ButtonType [text=Print Receipt, buttonData=OK_DONE]]")) {
                hospitalDetailController.deletePatientFinalDetail();
                addData(value);
                printReceipt();
            } else {

            }
        } else {
            new Alert(Alert.AlertType.CONFIRMATION, "Also Pay Money").show();
        }
    }


    private void addData(String value) throws SQLException, ClassNotFoundException {
        Patient pat = ManagePatient.getPatientDet(value);
        apoinmentDetail apoi = ManageApoinment.getApointmentDetail(value);
        PatientTm pTm = ManagePatient.getPatientTmDet(value);
        Doctor doc = ManageDoctor.getDocDetail(apoi.getDocId());
        Nurse nur = ManageNurse.getNurseDetail(apoi.getNurseId());
        String roomId = ManageRoom.getRoomDetail(value);
        docPatApoinmentDetail docPatDet = ManageDoctorPatientDetail.getDocPatDetail(value);

        String roomType = "null";
        double roomPrice = 0;
        if (roomId.equals("null")) {
        } else {
            Room room = ManageRoom.getRoomDeta(roomId);
            roomType = room.getType();
            roomPrice = room.getPrice();
        }

        String lab = "null";
        if (docPatDet.getLab().equals("null")) {
        } else {
            lab = ManageLab.getLabType(docPatDet.getLab());
        }

        String clothName = "null";
        double clothPrice = 0;
        if (docPatDet.getCloth().equals("null")) {
        } else {
            Cloth cloth = ManageCloth.getCloth(docPatDet.getCloth());
            clothName = cloth.getName();
            clothPrice = cloth.getPrice();
        }

        docPatApoinmentDetail apo = ManageDoctorPatientDetail.getDocPatDetail(value);

        String mediId = getMedName();
        String drId = getDrinkName();
        String eqId = getEqName();
        int medi = getMediPrice();
        int dr = getDrinkPriced();
        int eq = getEqPriced();

        FinalReportTbl fReport = new FinalReportTbl(
                apoi.getApoiId(), pat.getId(), pat.getName(), pat.getAddress(), String.valueOf(pTm.getAge()), pTm.getNic(),
                pTm.getContact(), doc.getName(), nur.getName(), roomType, roomPrice, lab, eqId, eq, drId, dr,
                clothName, clothPrice, mediId, String.valueOf(medi), apoi.getDate(), apoi.getTime(), apo.getPrice()
        );

        hospitalDetailController.setFinalPatientDetail(fReport);
    }

    public void printReceipt() throws SQLException, ClassNotFoundException {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/module/report/PatientFinal.jrxml"));
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

    private String getDrinkName() throws SQLException, ClassNotFoundException {
        String mediId = null;
        List<finalDrinkDetail> me = ManageDrink.getDrinkDetailId(value);
        for (int i = 0; i < me.size(); i++) {
            Drink q = ManageDrink.getDrink(me.get(i).getDrinkId());
            if (mediId != null) {
                mediId = mediId + " , " + q.getName();
            } else {
                mediId = q.getName();
            }
        }
        if (mediId == null) {
            mediId = "null";
        }
        return mediId;
    }

    private String getEqName() throws SQLException, ClassNotFoundException {
        String mediId = null;
        List<finalEqDetail> me = ManageEq.getEquipmentDetailId(value);
        for (int i = 0; i < me.size(); i++) {
            Equpment q = ManageEq.getEquipment(me.get(i).getEqId());
            if (mediId != null) {
                mediId = mediId + " , " + q.getName();
            } else {
                mediId = q.getName();
            }
        }
        if (mediId == null) {
            mediId = "null";
        }
        return mediId;
    }

    public String getMedName() throws SQLException, ClassNotFoundException {
        String mediId = null;
        List<finalMedicalDetail> me = ManageMedicine.getMedicalDetailId(value);
        for (int i = 0; i < me.size(); i++) {
            Medicine q = ManageMedicine.getMedicine(me.get(i).getMediId());
            if (mediId != null) {
                mediId = mediId + " , " + q.getName();
            } else {
                mediId = q.getName();
            }
        }
        if (mediId == null) {
            mediId = "null";
        }
        return mediId;
    }

    private int getMediPrice() throws SQLException, ClassNotFoundException {
        int temp = 0;
        List<finalMedicalDetail> i = ManageMedicine.getMedicineDetail(value);

        for (int j = 0; j < i.size(); j++) {
            temp += i.get(j).getPrice();
        }
        return temp;
    }

    public int getDrinkPriced() throws SQLException, ClassNotFoundException {
        int temp = 0;
        List<finalDrinkDetail> i = ManageDrink.getDrinkDetail(value);

        for (int j = 0; j < i.size(); j++) {
            temp += i.get(j).getPrice();
        }
        return temp;
    }

    public int getEqPriced() throws SQLException, ClassNotFoundException {
        int temp = 0;
        List<finalEqDetail> i = ManageEq.getEqDetail(value);

        for (int j = 0; j < i.size(); j++) {
            temp += i.get(j).getPrice();
        }
        return temp;
    }
}
