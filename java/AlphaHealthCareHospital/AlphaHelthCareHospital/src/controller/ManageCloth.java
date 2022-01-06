package controller;

import DBConnection.DBConnection;
import Tm.Cloth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageCloth {
    public static String getClothId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT clothId FROM Cloth ORDER BY clothId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                return "C-00" + tempId;
            } else if (tempId < 99) {
                return "C-0" + tempId;
            } else {
                return "C-" + tempId;
            }

        } else {
            return "C-001";
        }
    }

    public static boolean addNewCloth(String txtClothIdText, String value, String text, String text1) throws SQLException, ClassNotFoundException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("INSERT INTO Cloth VALUE (?,?,?,?)");
            pstm.setString(1, txtClothIdText);
            pstm.setString(2, text);
            pstm.setObject(3, value);
            pstm.setDouble(4, Double.parseDouble(text1));

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

    public static List<Cloth> getAllCloth() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Cloth");
        ResultSet rs = pstm.executeQuery();

        List<Cloth> medi = new ArrayList<>();

        while (rs.next()) {
            medi.add(new Cloth(
                    rs.getString("clothId"),
                    rs.getString("name"),
                    rs.getString("availability"),
                    rs.getDouble("price")
            ));
        }

        return medi;
    }

    public static boolean deleteCloth(Cloth medi) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Cloth WHERE clothId=?");
        pstm.setObject(1, medi.getId());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updateCloth(String text, String text1, String value, String text2) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Cloth SET name=?, availability=?, price=? WHERE clothId=?");
        pstm.setObject(1, text1);
        pstm.setObject(2, value);
        pstm.setDouble(3, Double.parseDouble(text2));
        pstm.setObject(4, text);
        return pstm.executeUpdate() > 0;

    }

    public static int getClothPrice(String cloth) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Cloth WHERE clothId=?");
        stm.setObject(1, cloth);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getInt(4);

        } else {
            return 0;
        }
    }

    public static Cloth getCloth(String cloth) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Cloth WHERE clothId=?");
        stm.setObject(1, cloth);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Cloth(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            );

        } else {
            return null;
        }
    }

    public List<String> getAllClothIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Cloth WHERE availability='"+"Available"+"'").executeQuery();
        List<String> slots = new ArrayList<>();
        while (rst.next()) {
            slots.add(
                    rst.getString(1)
            );
        }
        return slots;
    }
}
