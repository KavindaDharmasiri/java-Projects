package controller;

import Tm.docPatApoinmentDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.List;

public class ApoinmentDetailFormController {
    public AnchorPane apoinmentDetailDashBoard;
    public TableView<docPatApoinmentDetail> tblApoinmentDetail;
    public TableColumn colPatId;
    public TableColumn colDocId;
    public TableColumn colNurseId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colMedicine;
    public TableColumn colDrink;
    public TableColumn colCloth;
    public TableColumn colRoom;
    public TableColumn colLab;
    public TableColumn colParkingSlot;
    public TableColumn colApoinmentiD;
    public TableColumn colPatientName;
    public TableColumn colPrice;
    public TableColumn colSuccess;
    public TableColumn colEq;

    public void initialize() {
        tblApoinmentDetail.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ApointmentId"));
        tblApoinmentDetail.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("patientId"));
        tblApoinmentDetail.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("patientName"));
        tblApoinmentDetail.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("docId"));
        tblApoinmentDetail.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("nurseId"));
        tblApoinmentDetail.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblApoinmentDetail.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("time"));
        tblApoinmentDetail.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("medicine"));
        tblApoinmentDetail.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("eq"));
        tblApoinmentDetail.getColumns().get(9).setCellValueFactory(new PropertyValueFactory<>("drink"));
        tblApoinmentDetail.getColumns().get(10).setCellValueFactory(new PropertyValueFactory<>("cloth"));
        tblApoinmentDetail.getColumns().get(11).setCellValueFactory(new PropertyValueFactory<>("room"));
        tblApoinmentDetail.getColumns().get(12).setCellValueFactory(new PropertyValueFactory<>("lab"));
        tblApoinmentDetail.getColumns().get(13).setCellValueFactory(new PropertyValueFactory<>("parkingSlot"));
        tblApoinmentDetail.getColumns().get(14).setCellValueFactory(new PropertyValueFactory<>("price"));
        tblApoinmentDetail.getColumns().get(15).setCellValueFactory(new PropertyValueFactory<>("Success"));
        tblApoinmentDetail.getColumns().setAll(colApoinmentiD, colPatId, colPatientName, colDocId, colNurseId, colDate, colTime, colMedicine, colEq, colDrink, colCloth, colRoom, colLab, colParkingSlot, colPrice, colSuccess);

        tblApoinmentDetail.getItems().setAll(loadTableData());

    }

    public ObservableList<docPatApoinmentDetail> loadTableData() {
        try {

            List<docPatApoinmentDetail> allEq = ManageDoctorPatientDetail.getAllDocPatDetail();
            ObservableList<docPatApoinmentDetail> tableData = FXCollections.observableArrayList();
            for (docPatApoinmentDetail medi : allEq) {

                tableData.add(new docPatApoinmentDetail(
                        medi.getApointmentId(),
                        medi.getPatientId(),
                        medi.getPatientName(),
                        medi.getDocId(),
                        medi.getNurseId(),
                        medi.getDate(),
                        medi.getTime(),
                        medi.getMedicine(),
                        medi.getEq(),
                        medi.getDrink(),
                        medi.getCloth(),
                        medi.getRoom(),
                        medi.getLab(),
                        medi.getParkingSlot(),
                        medi.getPrice(),
                        medi.getSuccess()
                ));
            }

            return tableData;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadData() {
        tblApoinmentDetail.getItems().setAll(loadTableData());
    }
}
