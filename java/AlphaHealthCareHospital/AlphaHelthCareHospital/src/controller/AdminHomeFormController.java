package controller;

import Tm.docPatApoinmentDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdminHomeFormController {
    public static String name;
    public AnchorPane adminHomeDashBoard;
    public Label txtAdminName;
    public PieChart pieChart;
    private String temp;
    private double dailyincome = 0;
    private double monthlyincome = 0;
    private double yearlyincome = 0;

    public void initialize() {
        date();

        String day = temp.substring(0, 10);
        String month = temp.substring(3, 10);
        String year = temp.substring(6, 10);
        try {
            setDayValue(day);
            setMonthValue(month);
            setYearValue(year);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        initPieChart();

        txtAdminName.setText(name);
    }

    private void setYearValue(String year) throws SQLException, ClassNotFoundException {
        List<docPatApoinmentDetail> doc = hospitalDetailController.getFinalFromSuccess("Success");
        for (int i = 0; i < doc.size(); i++) {
            String yearDay = doc.get(i).getDate().substring(6, 10);
            if (yearDay.equals(year)) {
                yearlyincome += doc.get(i).getPrice();
            }
        }
    }

    private void setMonthValue(String month) throws SQLException, ClassNotFoundException {
        List<docPatApoinmentDetail> doc = hospitalDetailController.getFinalFromSuccess("Success");
        for (int i = 0; i < doc.size(); i++) {
            String monthDay = doc.get(i).getDate().substring(3, 10);
            if (monthDay.equals(month)) {
                monthlyincome += doc.get(i).getPrice();
            }
        }
    }

    private void setDayValue(String day) throws SQLException, ClassNotFoundException {
        List<docPatApoinmentDetail> doc = hospitalDetailController.getFinalFromSuccess("Success");
        for (int i = 0; i < doc.size(); i++) {
            if (doc.get(i).getDate().equals(day)) {
                dailyincome += doc.get(i).getPrice();
            }
        }
    }

    private void initPieChart() {
        ObservableList<PieChart.Data> pieChartdate = FXCollections.observableArrayList(
                new PieChart.Data("Daily", dailyincome),
                new PieChart.Data("Monthly", monthlyincome),
                new PieChart.Data("Yearly", yearlyincome)
        );
        pieChart.setData(pieChartdate);
    }


    private void date() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        temp = formatter.format(date);
    }
}
