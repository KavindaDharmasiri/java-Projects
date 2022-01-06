package controller;

import DBConnection.DBConnection;
import Tm.Drink;
import Tm.updateDrinkDetail;
import module.finalDrinkDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageDrink {
    public static String getDrinkId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT drinkId FROM Drink ORDER BY drinkId DESC LIMIT 1"
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

    public static boolean addNewDrink(String text, String text1, String value, String qty, String text2) throws SQLException, ClassNotFoundException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("INSERT INTO Drink VALUE (?,?,?,?,?)");
            pstm.setString(1, text);
            pstm.setString(2, text1);
            pstm.setObject(3, value);
            pstm.setInt(4, Integer.parseInt(qty));
            pstm.setDouble(5, Double.parseDouble(text2));

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

    public static List<Drink> getAllDrink() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Drink");
        ResultSet rs = pstm.executeQuery();

        List<Drink> medi = new ArrayList<>();

        while (rs.next()) {
            medi.add(new Drink(
                    rs.getString("drinkId"),
                    rs.getString("name"),
                    rs.getString("availability"),
                    rs.getInt("QtyOnHand"),
                    rs.getDouble("price")
            ));
        }

        return medi;
    }

    public static boolean deleteDrink(Drink medi) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Drink WHERE drinkId=?");
        pstm.setObject(1, medi.getId());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updateDrink(String text, String text1, String value, String text2) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Drink SET name=?, availability=?, price=? WHERE drinkId=?");
        pstm.setObject(1, text1);
        pstm.setObject(2, value);
        pstm.setDouble(3, Double.parseDouble(text2));
        pstm.setObject(4, text);
        return pstm.executeUpdate() > 0;

    }

    public static Drink getDrink(String drink) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Drink WHERE drinkId=?");
        stm.setObject(1, drink);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Drink(
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

    public static boolean placeDrinkDetail(String text, String id, int qty, double total) {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.
                    prepareStatement("INSERT INTO DrinkDetail VALUES(?,?,?,?)");


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

    public static List<finalDrinkDetail> getDrinkDetailId(String valueOne) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM DrinkDetail WHERE patId=?");
        stm.setObject(1, valueOne);

        ResultSet rst = stm.executeQuery();

        List<finalDrinkDetail> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new finalDrinkDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            ));

        }
        return customers;
    }

    public static List<updateDrinkDetail> getAllDrinkDetail(String lblPatId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM DrinkDetail WHERE patId=?");
        stm.setObject(1, lblPatId);

        ResultSet rst = stm.executeQuery();

        List<updateDrinkDetail> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new updateDrinkDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            ));

        }
        return customers;
    }

    public static boolean updateDrinkDetail(String temp, String text, String text1, double ttl) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE DrinkDetail SET Qty=? , price=? WHERE patId=? AND drinkId=?");
        pstm.setObject(1, text1);
        pstm.setObject(2, ttl);
        pstm.setObject(3, temp);
        pstm.setObject(4, text);
        return pstm.executeUpdate() > 0;
    }

    public static boolean deleteDrinkDetail(String drinkId, String patId) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM DrinkDetail WHERE patId=? AND drinkId=?");
        pstm.setObject(1, patId);
        pstm.setObject(2, drinkId);
        return pstm.executeUpdate() > 0;
    }

    public static boolean updateDrinkQty(String id, int newQty) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Drink SET QtyOnHand=? WHERE drinkId=?");
        pstm.setObject(1, newQty);
        pstm.setObject(2, id);
        return pstm.executeUpdate() > 0;
    }

    public static List<finalDrinkDetail> getDrinkDetail(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM DrinkDetail WHERE patId=?");
        stm.setObject(1, value);

        ResultSet rst = stm.executeQuery();

        List<finalDrinkDetail> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new finalDrinkDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            ));

        }
        return customers;
    }

    public List<String> getAllDrinkIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Drink WHERE availability='"+"Available"+"'").executeQuery();
        List<String> slots = new ArrayList<>();
        while (rst.next()) {
            slots.add(
                    rst.getString(1)
            );
        }
        return slots;
    }
}
