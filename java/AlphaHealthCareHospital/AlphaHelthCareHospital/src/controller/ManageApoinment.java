package controller;

import DBConnection.DBConnection;
import javafx.scene.control.TextField;
import module.apoinmentDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageApoinment {
    public static String getApoiId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT ApoinmentId FROM Apointment ORDER BY ApoinmentId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                return "A-00" + tempId;
            } else if (tempId < 99) {
                return "A-0" + tempId;
            } else {
                return "A-" + tempId;
            }

        } else {
            return "A-001";
        }
    }

    public static boolean addNewApoinment(String text, String valueOne, String valueTwo, String valueThree, TextField txtApoinmentTime, TextField txtApoinmentDate) throws SQLException, ClassNotFoundException {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("INSERT INTO Apointment VALUE (?,?,?,?,?,?)");
            pstm.setString(1, text);
            pstm.setString(2, valueOne);
            pstm.setString(3, valueTwo);
            pstm.setString(4, valueThree);
            pstm.setString(5, txtApoinmentTime.getText());
            pstm.setString(6, txtApoinmentDate.getText());
            if (pstm.executeUpdate() > 0) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {

                con.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }


    public static boolean addNewApoinmentDetail(String id, String valueOne, String name, String valueTwo, String valueFour, String valueSeven, String valueSix, String valueFive, String patId, String Patname, String docId, String nurseId, String date, String time, String room, String park, int total, String no) throws SQLException, ClassNotFoundException {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("INSERT INTO DoctorPatientDetail VALUE (?,?,?,?,?,?,?,?)");
            pstm.setString(1, id);
            pstm.setString(2, valueOne);
            pstm.setString(3, name);
            pstm.setString(4, valueTwo);
            pstm.setString(5, valueFour);
            pstm.setString(6, valueSeven);
            pstm.setString(7, valueSix);
            pstm.setString(8, valueFive);

            PreparedStatement pst = con.prepareStatement("INSERT INTO Final VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, id);
            pst.setString(2, patId);
            pst.setString(3, name);
            pst.setString(4, docId);
            pst.setString(5, nurseId);
            pst.setString(6, date);
            pst.setString(7, time);
            pst.setString(8, valueSix);
            pst.setString(9, valueFive);
            pst.setString(10, valueFour);
            pst.setString(11, valueSeven);
            pst.setString(12, id);
            pst.setString(13, valueTwo);
            pst.setString(14, park);
            pst.setDouble(15, total);
            pst.setString(16, no);

            if (pstm.executeUpdate() > 0 && pst.executeUpdate() > 0) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {

                con.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    public static apoinmentDetail getApointmentDetail(String valueOne) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Apointment WHERE patientId=?");
        stm.setObject(1, valueOne);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new apoinmentDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );

        } else {
            return null;
        }
    }


    public static List<apoinmentDetail> getAllApoinment() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Apointment");
        ResultSet rs = pstm.executeQuery();

        List<apoinmentDetail> medi = new ArrayList<>();

        while (rs.next()) {
            medi.add(new apoinmentDetail(
                    rs.getString("ApoinmentId"),
                    rs.getString("patientId"),
                    rs.getString("docId"),
                    rs.getString("nurseId"),
                    rs.getString("time"),
                    rs.getString("date")
            ));
        }
        return medi;
    }

    public static boolean deleteApoinment(apoinmentDetail medi) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Apointment WHERE patientId=?");
        pstm.setObject(1, medi.getPatId());
        return pstm.executeUpdate() > 0;

    }

    public static boolean updateApoinment(String text, TextField txtApoinmentTime, TextField txtApoinmentDate) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Apointment SET time=? , date=? WHERE apoinmentId=?");

        pstm.setObject(1, txtApoinmentTime.getText());
        pstm.setObject(2, txtApoinmentDate.getText());
        pstm.setObject(3, text);
        return pstm.executeUpdate() > 0;
    }

    public static List<apoinmentDetail> searchApoinment(String value) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Apointment WHERE docId LIKE '%" + value + "%'");
        ResultSet rs = pstm.executeQuery();

        List<apoinmentDetail> customers = new ArrayList<>();

        while (rs.next()) {
            customers.add(new apoinmentDetail(
                    rs.getString("apoinmentId"),
                    rs.getString("patientId"),
                    rs.getString("docId"),
                    rs.getString("nurseId"),
                    rs.getString("time"),
                    rs.getString("date")
            ));
        }

        return customers;
    }

    public List<String> getAllPatientApoiIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Apointment").executeQuery();
        List<String> slots = new ArrayList<>();
        while (rst.next()) {
            slots.add(
                    rst.getString(2)
            );
        }
        return slots;
    }
}
