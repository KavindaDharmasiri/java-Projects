package controller;

import Tm.PatientTm;
import Tm.viewPatientApointment;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import module.apoinmentDetail;

import java.sql.SQLException;
import java.util.List;

public class ViewApoinmentFormController {
    public AnchorPane viewApoinmentDashBoard;
    public TableView<viewPatientApointment> tblApoinmentDetail;
    public TableColumn colPatId;
    public TableColumn colPatName;
    public TableColumn colPatAge;
    public TableColumn colDate;
    public TableColumn colTime;
    public JFXTextField txtSearch;

    public void initialize() {
        tblApoinmentDetail.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("patId"));
        tblApoinmentDetail.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("patName"));
        tblApoinmentDetail.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("age"));
        tblApoinmentDetail.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblApoinmentDetail.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("time"));

        tblApoinmentDetail.getColumns().setAll(colPatId, colPatName, colPatAge, colDate, colTime);

    }

    public void txtSearch_OnAction(KeyEvent actionEvent) {
        search(txtSearch.getText());
    }

    private void search(String value) {
        try {
            List<apoinmentDetail> customers = ManageApoinment.searchApoinment(value);
            ObservableList<viewPatientApointment> tableData = FXCollections.observableArrayList();
            for (apoinmentDetail customer : customers) {

                PatientTm pat = ManagePatient.getPatientTmDet(customer.getPatId());

                tableData.add(new viewPatientApointment(
                        customer.getPatId(),
                        pat.getName(),
                        pat.getAge(),
                        customer.getDate(),
                        customer.getTime()
                ));
            }
            tblApoinmentDetail.getItems().setAll(tableData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
