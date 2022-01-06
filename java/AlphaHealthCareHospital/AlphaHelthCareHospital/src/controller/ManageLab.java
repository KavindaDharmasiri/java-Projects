package controller;

import DBConnection.DBConnection;
import Tm.Lab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageLab {
    public static String getLabId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT labId FROM Lab ORDER BY labId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                return "L-00" + tempId;
            } else if (tempId < 99) {
                return "L-0" + tempId;
            } else {
                return "L-" + tempId;
            }

        } else {
            return "L-001";
        }
    }

    public static boolean addLab(String txtLabIdText, String text, String value) throws SQLException, ClassNotFoundException {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("INSERT INTO Lab VALUE (?,?,?)");
            pstm.setString(1, txtLabIdText);
            pstm.setString(2, text);
            pstm.setObject(3, value);

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

    public static List<Lab> getAllLab() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Lab");
        ResultSet rs = pstm.executeQuery();

        List<Lab> medi = new ArrayList<>();

        while (rs.next()) {
            medi.add(new Lab(
                    rs.getString("labId"),
                    rs.getString("type"),
                    rs.getString("availability")
            ));
        }

        return medi;
    }

    public static boolean deleteLab(Lab medi) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Lab WHERE labId=?");
        pstm.setObject(1, medi.getId());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updateLab(String text, String text1, String value) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Lab SET type=? , availability=? WHERE labId=?");
        pstm.setObject(1, text1);
        pstm.setObject(2, value);
        pstm.setObject(3, text);
        return pstm.executeUpdate() > 0;

    }

    public static String getLabType(String lab) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Lab WHERE labId=?");
        stm.setObject(1, lab);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getString(2);

        } else {
            return "null";
        }
    }

    public List<String> getAllLabIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Lab WHERE availability='"+"Available"+"'").executeQuery();
        List<String> slots = new ArrayList<>();
        while (rst.next()) {
            slots.add(
                    rst.getString(1)
            );
        }
        return slots;
    }
}
