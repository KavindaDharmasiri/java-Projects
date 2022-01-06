package controller;

import DBConnection.DBConnection;
import Tm.docPatApoinmentDetail;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReportFormController {
    public AnchorPane reportDashBoard;
    public Label txtDailyIncome;
    public Label txtMonthlyIncome;
    public Label txtyearlyIncome;
    public JFXComboBox cmbPatientId;
    public Label txtcmbPatientWiseIncome;
    private String value;
    private double dailyincome = 0;
    private double monthlyincome = 0;
    private double yearlyincome = 0;
    private String temp;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadPatientApoiIds();
        date();

        String day = temp.substring(0, 10);
        String month = temp.substring(3, 10);
        String year = temp.substring(6, 10);


        cmbPatientId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            value = (String) newValue;

            try {
                docPatApoinmentDetail det = ManageDoctorPatientDetail.getFinal(value);
                txtcmbPatientWiseIncome.setText(String.valueOf(det.getPrice()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        setDayValue(day);
        setMonthValue(month);
        setYearValue(year);
    }

    private void setYearValue(String year) throws SQLException, ClassNotFoundException {
        List<docPatApoinmentDetail> doc = hospitalDetailController.getFinalFromSuccess("Success");
        for (int i = 0; i < doc.size(); i++) {
            String yearDay = doc.get(i).getDate().substring(6, 10);
            if (yearDay.equals(year)) {
                yearlyincome += doc.get(i).getPrice();
            }
        }
        txtyearlyIncome.setText(String.valueOf(yearlyincome));
    }

    private void setMonthValue(String month) throws SQLException, ClassNotFoundException {
        List<docPatApoinmentDetail> doc = hospitalDetailController.getFinalFromSuccess("Success");
        for (int i = 0; i < doc.size(); i++) {
            String monthDay = doc.get(i).getDate().substring(3, 10);
            if (monthDay.equals(month)) {
                monthlyincome += doc.get(i).getPrice();
            }
        }
        txtMonthlyIncome.setText(String.valueOf(monthlyincome));
    }

    private void setDayValue(String day) throws SQLException, ClassNotFoundException {
        List<docPatApoinmentDetail> doc = hospitalDetailController.getFinalFromSuccess("Success");
        for (int i = 0; i < doc.size(); i++) {
            if (doc.get(i).getDate().equals(day)) {
                dailyincome += doc.get(i).getPrice();
            }
        }
        txtDailyIncome.setText(String.valueOf(dailyincome));

    }

    private void date() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        temp = formatter.format(date);
    }

    private void loadPatientApoiIds() throws SQLException, ClassNotFoundException {
        List<String> patIds = new hospitalDetailController().getAllPatientFinalApoiIds();
        cmbPatientId.getItems().addAll(patIds);
    }

    public void printReportOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        hospitalDetailController.deleteFinalReport();
        hospitalDetailController.addFinalReport(dailyincome, monthlyincome, yearlyincome);
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/module/report/FinalReportMoney.jrxml"));
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
}
