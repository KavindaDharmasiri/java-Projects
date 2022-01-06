package controller;

import DBConnection.DBConnection;
import Tm.Equpment;
import Tm.updateEqDetail;
import module.finalEqDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageEq {
    public static String getEquipmentId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT equpmentId FROM SurgicalEqupment ORDER BY equpmentId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                return "E-00" + tempId;
            } else if (tempId < 99) {
                return "E-0" + tempId;
            } else {
                return "E-" + tempId;
            }

        } else {
            return "E-001";
        }
    }

    public static boolean addNewEquipment(String text, String txtEquipmentNameText, String value, String txtQtyText, String txtEquipmentPriceText) throws SQLException, ClassNotFoundException {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("INSERT INTO SurgicalEqupment VALUE (?,?,?,?,?)");
            pstm.setString(1, text);
            pstm.setString(2, txtEquipmentNameText);
            pstm.setObject(3, value);
            pstm.setInt(4, Integer.parseInt(txtQtyText));
            pstm.setDouble(5, Double.parseDouble(txtEquipmentPriceText));

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

    public static List<Equpment> getAllEqupment() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM SurgicalEqupment");
        ResultSet rs = pstm.executeQuery();

        List<Equpment> medi = new ArrayList<>();

        while (rs.next()) {
            medi.add(new Equpment(
                    rs.getString("equpmentId"),
                    rs.getString("name"),
                    rs.getString("availability"),
                    rs.getInt("QtyOnHand"),
                    rs.getDouble("price")
            ));
        }

        return medi;
    }

    public static boolean deleteEqupment(Equpment medi) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM SurgicalEqupment WHERE equpmentId=?");
        pstm.setObject(1, medi.getId());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updateEqpment(String text, String text1, String value, String text2) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE SurgicalEqupment SET name=?, availability=?, price=? WHERE equpmentId=?");
        pstm.setObject(1, text1);
        pstm.setObject(2, value);
        pstm.setDouble(3, Double.parseDouble(text2));
        pstm.setObject(4, text);
        return pstm.executeUpdate() > 0;

    }

    public static boolean placeEqDetail(String text, String id, int qty, double total) {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.
                    prepareStatement("INSERT INTO SurgicalEqupmentDetail VALUES(?,?,?,?)");


            stm.setObject(1, text);
            stm.setObject(2, id);
            stm.setObject(3, qty);
            stm.setObject(4, total);

            if (stm.executeUpdate() > 0) {
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

    public static List<finalEqDetail> getEquipmentDetailId(String valueOne) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM SurgicalEqupmentDetail WHERE patId=?");
        stm.setObject(1, valueOne);

        ResultSet rst = stm.executeQuery();

        List<finalEqDetail> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new finalEqDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            ));

        }
        return customers;
    }

    public static Equpment getEquipment(String valueOne) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM SurgicalEqupment WHERE equpmentId=?");
        stm.setObject(1, valueOne);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Equpment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5)
            );

        } else {
            return null;
        }
    }

    public static boolean updateEqDetail(String temp, String text, String text1, double total) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE SurgicalEqupmentDetail SET Qty=? , price=? WHERE patId=? AND equpmentId=?");
        pstm.setObject(1, text1);
        pstm.setObject(2, total);
        pstm.setObject(3, temp);
        pstm.setObject(4, text);
        return pstm.executeUpdate() > 0;
    }

    public static List<updateEqDetail> getAllEqDetail(String temp) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM SurgicalEqupmentDetail WHERE patId=?");
        stm.setObject(1, temp);

        ResultSet rst = stm.executeQuery();

        List<updateEqDetail> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new updateEqDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            ));

        }
        return customers;
    }

    public static boolean updateEqQty(String id, int newQty) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE SurgicalEqupment SET QtyOnHand=? WHERE equpmentId=?");
        pstm.setObject(1, newQty);
        pstm.setObject(2, id);
        return pstm.executeUpdate() > 0;
    }

    public static List<finalEqDetail> getEqDetail(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM SurgicalEqupmentDetail WHERE patId=?");
        stm.setObject(1, value);

        ResultSet rst = stm.executeQuery();

        List<finalEqDetail> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new finalEqDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            ));

        }
        return customers;
    }

    public List<String> getAllEqIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM SurgicalEqupment WHERE availability='"+"Available"+"'").executeQuery();
        List<String> slots = new ArrayList<>();
        while (rst.next()) {
            slots.add(
                    rst.getString(1)
            );
        }
        return slots;
    }
}
