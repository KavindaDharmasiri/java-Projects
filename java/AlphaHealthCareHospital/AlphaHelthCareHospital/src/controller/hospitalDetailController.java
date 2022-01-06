package controller;

import DBConnection.DBConnection;
import Tm.Park;
import Tm.docPatApoinmentDetail;
import module.FinalReportTbl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class hospitalDetailController {
    public static boolean addNewPark(String text, String value) throws SQLException, ClassNotFoundException {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("INSERT INTO Parking VALUE (?,?)");
            pstm.setString(1, text);
            pstm.setObject(2, value);

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

    public static boolean addparkingdetail(String text, String valueTwo) throws SQLException, ClassNotFoundException {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("INSERT INTO ParkingDetail VALUE (?,?)");
            pstm.setString(1, text);
            pstm.setString(2, valueTwo);
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

    public static List<Park> getAllPark() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Parking");
        ResultSet rs = pstm.executeQuery();

        List<Park> medi = new ArrayList<>();

        while (rs.next()) {
            medi.add(new Park(
                    rs.getString("parkingSlot"),
                    rs.getString("availability")
            ));
        }

        return medi;
    }

    public static boolean deletePark(Park medi) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Parking WHERE parkingSlot=?");
        pstm.setObject(1, medi.getSlot());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updatePark(String text, String value) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Parking SET availability=? WHERE parkingSlot=?");
        pstm.setObject(1, value);
        pstm.setObject(2, text);
        return pstm.executeUpdate() > 0;

    }

    public static String getParkDet(String patId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM ParkingDetail WHERE patId=?");
        stm.setObject(1, patId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getString(2);

        } else {
            return "null";
        }
    }

    public static boolean updateFinalDetail(String success, String id) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Final SET success=? WHERE patientId=?");
        pstm.setObject(1, success);
        pstm.setObject(2, id);
        return pstm.executeUpdate() > 0;
    }

    public static boolean deletePatientFinalDetail() throws SQLException, ClassNotFoundException {

        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM PatientFinal");

        return pstm.executeUpdate() > 0;
    }

    public static boolean setFinalPatientDetail(FinalReportTbl fReport) {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("INSERT INTO PatientFinal VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstm.setString(1, fReport.getApoiId());
            pstm.setString(2, fReport.getPatId());
            pstm.setString(3, fReport.getPatName());
            pstm.setString(4, fReport.getPatAddress());
            pstm.setString(5, fReport.getPatAge());
            pstm.setString(6, fReport.getNic());
            pstm.setString(7, fReport.getContact());
            pstm.setString(8, fReport.getDocName());
            pstm.setString(9, fReport.getNurseName());
            pstm.setString(10, fReport.getRoomType());
            pstm.setDouble(11, fReport.getRoomPrice());
            pstm.setString(12, fReport.getLabType());
            pstm.setString(13, fReport.getEqName());
            pstm.setDouble(14, fReport.getEqPrice());
            pstm.setString(15, fReport.getDrinkName());
            pstm.setDouble(16, fReport.getDrinkPrice());
            pstm.setString(17, fReport.getClothName());
            pstm.setDouble(18, fReport.getClothPrice());
            pstm.setString(19, fReport.getMediName());
            pstm.setString(20, fReport.getMediPrice());
            pstm.setString(21, fReport.getDate());
            pstm.setString(22, fReport.getTime());
            pstm.setDouble(23, fReport.getFullTotal());
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

    public static List<docPatApoinmentDetail> getFinalFromSuccess(String success) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Final WHERE success=?");
        stm.setObject(1, success);

        ResultSet rst = stm.executeQuery();

        List<docPatApoinmentDetail> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new docPatApoinmentDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9),
                    rst.getString(10),
                    rst.getString(11),
                    rst.getString(12),
                    rst.getString(13),
                    rst.getString(14),
                    rst.getDouble(15),
                    rst.getString(16)
            ));

        }
        return customers;
    }

    public static boolean deleteFinalReport() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM finalReport");
        return pstm.executeUpdate() > 0;
    }

    public static boolean addFinalReport(double dailyincome, double monthlyincome, double yearlyincome) {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("INSERT INTO finalReport VALUE (?,?,?)");
            pstm.setString(1, dailyincome + "/=");
            pstm.setString(2, monthlyincome + "/=");
            pstm.setString(3, yearlyincome + "/=");
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

    public static boolean deleteFromApointment() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM ApointmentDetailForReport");
        return pstm.executeUpdate() > 0;
    }

    public static boolean addApoinmentDetailForReport(String text, String patName, String docName, String nurseName, String text1, String text2) {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("INSERT INTO ApointmentDetailForReport VALUE (?,?,?,?,?,?)");
            pstm.setString(1, text);
            pstm.setString(2, patName);
            pstm.setString(3, docName);
            pstm.setString(4, nurseName);
            pstm.setString(5, text1);
            pstm.setString(6, text2);
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

    public List<String> getAllParkSlots() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Parking WHERE availability='"+"Available"+"'").executeQuery();
        List<String> slots = new ArrayList<>();
        while (rst.next()) {
            slots.add(
                    rst.getString(1)
            );
        }
        return slots;
    }

    public List<String> getAllPatientFinalApoiIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Final").executeQuery();
        List<String> slots = new ArrayList<>();
        while (rst.next()) {
            slots.add(
                    rst.getString(2)
            );
        }
        return slots;
    }
}
