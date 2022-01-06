package controller;

import DBConnection.DBConnection;
import Tm.DocPatDetail;
import Tm.docPatApoinmentDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageDoctorPatientDetail {
    public static List<DocPatDetail> getAllDocpatDetail() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM DoctorPatientDetail");
        ResultSet rs = pstm.executeQuery();

        List<DocPatDetail> medi = new ArrayList<>();

        while (rs.next()) {
            medi.add(new DocPatDetail(
                    rs.getString("apoinmentId"),
                    rs.getString("patientId"),
                    rs.getString("pName"),
                    rs.getString("labId"),
                    rs.getString("drinkId"),
                    rs.getString("clothId"),
                    rs.getString("medicineId"),
                    rs.getString("eqId")
            ));
        }
        return medi;
    }

    public static boolean deleteDocPatDetail(DocPatDetail medi) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM DoctorPatientDetail WHERE apoinmentId=?");
        pstm.setObject(1, medi.getApoinmentId());

        PreparedStatement pst = con.prepareStatement("DELETE FROM Final WHERE ApointmentId=?");
        pst.setObject(1, medi.getApoinmentId());

        PreparedStatement rst = con.prepareStatement("DELETE FROM MedicineDetail WHERE patId=?");
        rst.setObject(1, medi.getPatid());

        PreparedStatement est = con.prepareStatement("DELETE FROM DrinkDetail WHERE patId=?");
        est.setObject(1, medi.getPatid());

        PreparedStatement tst = con.prepareStatement("DELETE FROM SurgicalEqupmentDetail WHERE patId=?");
        tst.setObject(1, medi.getPatid());

        rst.executeUpdate();
        est.executeUpdate();
        tst.executeUpdate();
        pstm.executeUpdate();
        return pst.executeUpdate() > 0 ;
    }

    public static boolean updateDocPatDetail(String valueTwo, String valueFour, String valueSeven, String valueSix, String valueFive, String text,int price) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE DoctorPatientDetail SET labId=? , drinkId=? , clothId=? , medicineId=?, eqId=? WHERE patientId=?");
        pstm.setObject(1, valueTwo);
        pstm.setObject(2, valueFour);
        pstm.setObject(3, valueSeven);
        pstm.setObject(4, valueSix);
        pstm.setObject(5, valueFive);
        pstm.setObject(6, text);

        PreparedStatement pst = con.prepareStatement("UPDATE Final SET lab=? , drink=? , cloth=? , medicine=? ,eq=? ,price =? WHERE patientId=?");
        pst.setObject(1, valueTwo);
        pst.setObject(2, valueFour);
        pst.setObject(3, valueSeven);
        pst.setObject(4, valueSix);
        pst.setObject(5, valueFive);
        pst.setObject(6, price);
        pst.setObject(7, text);
        return pst.executeUpdate() > 0 && pstm.executeUpdate() > 0;
    }

    public static List<docPatApoinmentDetail> getAllDocPatDetail() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Final");
        ResultSet rs = pstm.executeQuery();

        List<docPatApoinmentDetail> medi = new ArrayList<>();

        while (rs.next()) {
            medi.add(new docPatApoinmentDetail(
                    rs.getString("ApointmentId"),
                    rs.getString("patientId"),
                    rs.getString("patientName"),
                    rs.getString("docId"),
                    rs.getString("nurseId"),
                    rs.getString("date"),
                    rs.getString("time"),
                    rs.getString("medicine"),
                    rs.getString("eq"),
                    rs.getString("drink"),
                    rs.getString("cloth"),
                    rs.getString("room"),
                    rs.getString("lab"),
                    rs.getString("parkingSlot"),
                    rs.getDouble("price"),
                    rs.getString("success")
            ));
        }
        return medi;
    }

    public static docPatApoinmentDetail getFinal(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Final WHERE patientId=?");
        stm.setObject(1, value);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new docPatApoinmentDetail(
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
            );

        } else {
            return null;
        }
    }

    public static docPatApoinmentDetail getDocPatDetail(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Final WHERE patientId=?");
        stm.setObject(1, value);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new docPatApoinmentDetail(
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
            );

        } else {
            return null;
        }
    }

}
