package controller;

import DBConnection.DBConnection;
import Tm.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageDoctor {
    public static String getDocId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT docId FROM Doctor ORDER BY docId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                return "D-00" + tempId;
            } else if (tempId < 99) {
                return "D-0" + tempId;
            } else {
                return "D-" + tempId;
            }

        } else {
            return "D-001";
        }
    }

    public static boolean addNewDoc(String text, String text1, String text2, String text3, String text4, String text5) throws SQLException, ClassNotFoundException {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("INSERT INTO Doctor VALUE (?,?,?,?,?,?)");
            pstm.setString(1, text);
            pstm.setString(2, text1);
            pstm.setObject(3, text2);
            pstm.setString(4, text3);
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

    public static List<Doctor> getAllDoctor() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Doctor");
        ResultSet rs = pstm.executeQuery();

        List<Doctor> medi = new ArrayList<>();

        while (rs.next()) {
            medi.add(new Doctor(
                    rs.getString("docId"),
                    rs.getString("docName"),
                    rs.getString("specialist"),
                    rs.getString("address"),
                    rs.getString("nic"),
                    rs.getString("contact")
            ));
        }
        return medi;
    }

    public static boolean deleteDoctor(Doctor medi) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Doctor WHERE docId=?");
        pstm.setObject(1, medi.getId());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updateDoctor(String text, String text1, String text2, String text3, String text4, String text5) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Doctor SET docName=? , specialist=? , address=? , nic=?, contact=? WHERE docId=?");
        pstm.setObject(1, text1);
        pstm.setObject(2, text2);
        pstm.setObject(3, text3);
        pstm.setObject(4, text4);
        pstm.setObject(5, text5);
        pstm.setObject(6, text);
        return pstm.executeUpdate() > 0;
    }

    public static Doctor getDocDetail(String docId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Doctor WHERE docId=?");
        stm.setObject(1, docId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Doctor(
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

    public List<String> getAllDoctorIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Doctor").executeQuery();
        List<String> slots = new ArrayList<>();
        while (rst.next()) {
            slots.add(
                    rst.getString(1)
            );
        }
        return slots;
    }
}
