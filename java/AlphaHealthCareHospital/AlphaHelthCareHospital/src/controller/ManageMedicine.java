package controller;

import DBConnection.DBConnection;
import Tm.Medicine;
import Tm.updateMedicineForm;
import module.finalMedicalDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageMedicine {
    public static String getMedicineId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT medicineId FROM Medicine ORDER BY medicineId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                return "M-00" + tempId;
            } else if (tempId < 99) {
                return "M-0" + tempId;
            } else {
                return "M-" + tempId;
            }

        } else {
            return "M-001";
        }
    }

    public static boolean addNewMedicine(String text, String text1, String value, String text2, String qty) throws SQLException, ClassNotFoundException {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("INSERT INTO Medicine VALUE (?,?,?,?,?)");
            pstm.setString(1, text);
            pstm.setString(2, text1);
            pstm.setObject(3, value);
            pstm.setDouble(4, Integer.parseInt(qty));
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

    public static List<Medicine> getAllMedicine() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Medicine");
        ResultSet rs = pstm.executeQuery();

        List<Medicine> medi = new ArrayList<>();

        while (rs.next()) {
            medi.add(new Medicine(
                    rs.getString("medicineId"),
                    rs.getString("name"),
                    rs.getString("availability"),
                    rs.getString("QtyOnHand"),
                    rs.getString("price")
            ));
        }

        return medi;
    }

    public static boolean deleteMedicine(Medicine item) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Medicine WHERE medicineId=?");
        pstm.setObject(1, item.getId());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updateMedicine(String text, String text1, String value, String text2, String qty) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Medicine SET name=?, availability=?, price=? ,QtyOnHand=? WHERE medicineId=?");
        pstm.setObject(1, text1);
        pstm.setObject(2, value);
        pstm.setDouble(3, Double.parseDouble(text2));
        pstm.setDouble(4, Integer.parseInt(qty));
        pstm.setObject(5, text);
        return pstm.executeUpdate() > 0;

    }

    public static Medicine getMedicine(String medicine) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Medicine WHERE medicineId=?");
        stm.setObject(1, medicine);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Medicine(
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

    public static boolean placeMedicalDetail(String text, String id, int qty, double total) {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.
                    prepareStatement("INSERT INTO MedicineDetail VALUES(?,?,?,?)");


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

    public static List<finalMedicalDetail> getMedicalDetailId(String valueOne) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM MedicineDetail WHERE patId=?");
        stm.setObject(1, valueOne);

        ResultSet rst = stm.executeQuery();

        List<finalMedicalDetail> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new finalMedicalDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            ));

        }
        return customers;
    }

    public static boolean deleteMediDetail(String mediId, String patId) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM MedicineDetail WHERE patId=? AND medicineId=?");
        pstm.setObject(1, patId);
        pstm.setObject(2, mediId);
        return pstm.executeUpdate() > 0;
    }

    public static List<updateMedicineForm> getAllMediDetail(String temp) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM MedicineDetail WHERE patId=?");
        stm.setObject(1, temp);

        ResultSet rst = stm.executeQuery();

        List<updateMedicineForm> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new updateMedicineForm(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            ));

        }
        return customers;
    }

    public static boolean updateMediDetail(String temp, String text, String text1, double total) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE MedicineDetail SET Qty=? , price=? WHERE patId=? AND medicineId=?");
        pstm.setObject(1, text1);
        pstm.setObject(2, total);
        pstm.setObject(3, temp);
        pstm.setObject(4, text);
        return pstm.executeUpdate() > 0;
    }

    public static boolean deleteEqDetail(String eqId, String patId) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM SurgicalEqupmentDetail WHERE patId=? AND equpmentId=?");
        pstm.setObject(1, patId);
        pstm.setObject(2, eqId);
        return pstm.executeUpdate() > 0;
    }

    public static boolean updateMedicineQty(String id, int newQty) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Medicine SET QtyOnHand=? WHERE medicineId=?");
        pstm.setObject(1, newQty);
        pstm.setObject(2, id);
        return pstm.executeUpdate() > 0;
    }

    public static List<finalMedicalDetail> getMedicineDetail(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM MedicineDetail WHERE patId=?");
        stm.setObject(1, value);

        ResultSet rst = stm.executeQuery();

        List<finalMedicalDetail> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new finalMedicalDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            ));

        }
        return customers;
    }

    public List<String> getAllMediIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Medicine WHERE availability='"+"Available"+"'").executeQuery();
        List<String> slots = new ArrayList<>();
        while (rst.next()) {
            slots.add(
                    rst.getString(1)
            );
        }
        return slots;
    }

}
