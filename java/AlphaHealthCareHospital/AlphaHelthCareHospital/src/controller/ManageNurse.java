package controller;

import DBConnection.DBConnection;
import Tm.Nurse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageNurse {
    public static String getNurseId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT nurseId FROM Nurse ORDER BY nurseId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                return "N-00" + tempId;
            } else if (tempId < 99) {
                return "N-0" + tempId;
            } else {
                return "N-" + tempId;
            }

        } else {
            return "N-001";
        }
    }

    public static boolean addNewNurse(String text, String text1, String text2, String text3, String text4) throws SQLException, ClassNotFoundException {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("INSERT INTO Nurse VALUE (?,?,?,?,?)");
            pstm.setString(1, text);
            pstm.setString(2, text1);
            pstm.setObject(3, text2);
            pstm.setString(4, text3);
            pstm.setString(5, text4);
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

    public static List<Nurse> getAllNurse() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Nurse");
        ResultSet rs = pstm.executeQuery();

        List<Nurse> medi = new ArrayList<>();

        while (rs.next()) {
            medi.add(new Nurse(
                    rs.getString("nurseId"),
                    rs.getString("nurseName"),
                    rs.getString("address"),
                    rs.getString("nic"),
                    rs.getString("contact")
            ));
        }
        return medi;
    }

    public static boolean deleteNurse(Nurse medi) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Nurse WHERE nurseId=?");
        pstm.setObject(1, medi.getId());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updateNurse(String text, String text1, String text2, String text3, String text4) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Nurse SET nurseName=? , address=? , nic=?, contact=? WHERE nurseId=?");
        pstm.setObject(1, text1);
        pstm.setObject(2, text2);
        pstm.setObject(3, text3);
        pstm.setObject(4, text4);
        pstm.setObject(5, text);
        return pstm.executeUpdate() > 0;
    }

    public static Nurse getNurseDetail(String nurseId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Nurse WHERE nurseId=?");
        stm.setObject(1, nurseId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Nurse(
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

    public List<String> getAllNurseIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Nurse").executeQuery();
        List<String> slots = new ArrayList<>();
        while (rst.next()) {
            slots.add(
                    rst.getString(1)
            );
        }
        return slots;
    }
}
