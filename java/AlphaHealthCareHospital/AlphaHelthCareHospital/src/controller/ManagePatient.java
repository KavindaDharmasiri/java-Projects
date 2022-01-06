package controller;

import DBConnection.DBConnection;
import Tm.PatientTm;
import module.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagePatient {
    public static String getPatId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT patientId FROM Patient ORDER BY patientId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                return "P-00" + tempId;
            } else if (tempId < 99) {
                return "P-0" + tempId;
            } else {
                return "P-" + tempId;
            }

        } else {
            return "P-001";
        }
    }

    public static boolean addNewPat(String text, String text1, String text2, String text3, String text4, String text5) throws SQLException, ClassNotFoundException {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("INSERT INTO Patient VALUE (?,?,?,?,?,?)");
            pstm.setString(1, text);
            pstm.setString(2, text1);
            pstm.setObject(3, text2);
            pstm.setInt(4, Integer.parseInt(text3));
            pstm.setString(5, text4);
            pstm.setString(6, text5);
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

    public static Patient getPatientName(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Patient WHERE patientId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Patient(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );

        } else {
            return null;
        }
    }

    public static List<PatientTm> getAllPatient() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Patient");
        ResultSet rs = pstm.executeQuery();

        List<PatientTm> medi = new ArrayList<>();

        while (rs.next()) {
            medi.add(new PatientTm(
                    rs.getString("patientId"),
                    rs.getString("patientName"),
                    rs.getString("address"),
                    rs.getInt("age"),
                    rs.getString("nic"),
                    rs.getString("contact")
            ));
        }
        return medi;
    }

    public static boolean deletePatient(PatientTm medi) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Patient WHERE patientId=?");
        pstm.setObject(1, medi.getId());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updatePatient(String text, String text1, String text2, String text3, String text4, String text5) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Patient SET patientName=? , address=? , age=? , nic=?, contact=? WHERE patientId=?");
        pstm.setObject(1, text1);
        pstm.setObject(2, text2);
        pstm.setObject(3, text3);
        pstm.setObject(4, text4);
        pstm.setObject(5, text5);
        pstm.setObject(6, text);
        return pstm.executeUpdate() > 0;
    }

    public static Patient getPatientDet(String patId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Patient WHERE patientId=?");
        stm.setObject(1, patId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Patient(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );

        } else {
            return null;
        }
    }

    public static PatientTm getPatientTmDet(String patId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Patient WHERE patientId=?");
        stm.setObject(1, patId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new PatientTm(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getString(5),
                    rst.getString(6)
            );

        } else {
            return null;
        }
    }

    public List<String> getAllPatientIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Patient").executeQuery();
        List<String> slots = new ArrayList<>();
        while (rst.next()) {
            slots.add(
                    rst.getString(1)
            );
        }
        return slots;
    }

}
