package controller;

import DBConnection.DBConnection;
import Tm.Room;
import javafx.scene.control.TextField;
import module.RoomDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageRoom {
    public static String getRoomId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT roomId FROM Room ORDER BY roomId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                return "R-00" + tempId;
            } else if (tempId < 99) {
                return "R-0" + tempId;
            } else {
                return "R-" + tempId;
            }

        } else {
            return "R-001";
        }
    }

    public static boolean addNewRoom(String text, String text1, String value, String text2) throws SQLException, ClassNotFoundException {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("INSERT INTO Room VALUE (?,?,?,?)");
            pstm.setString(1, text);
            pstm.setString(2, text1);
            pstm.setObject(3, value);
            pstm.setDouble(4, Double.parseDouble(text2));
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

    public static boolean addNewRoomDetail(TextField txtPatientId, String valueOne) throws SQLException, ClassNotFoundException {
        RoomDetail text = getRoomPrice(valueOne);
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("INSERT INTO RoomDetail VALUE (?,?,?)");
            pstm.setString(1, txtPatientId.getText());
            pstm.setString(2, valueOne);
            pstm.setObject(3, text.getPrice());
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

    private static RoomDetail getRoomPrice(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Room WHERE roomId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new RoomDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );

        } else {
            return null;
        }
    }

    public static boolean deleteRoom(Room medi) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Room WHERE roomId=?");
        pstm.setObject(1, medi.getId());
        return pstm.executeUpdate() > 0;
    }

    public static List<Room> getAllRoom() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Room");
        ResultSet rs = pstm.executeQuery();

        List<Room> medi = new ArrayList<>();

        while (rs.next()) {
            medi.add(new Room(
                    rs.getString("roomId"),
                    rs.getString("type"),
                    rs.getString("availability"),
                    rs.getDouble("price")
            ));
        }

        return medi;
    }

    public static boolean updateRoom(String text, String text1, String value, String text2) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Room SET type=? , availability=? , price=? WHERE roomId=?");
        pstm.setObject(1, text1);
        pstm.setObject(2, value);
        pstm.setObject(3, text2);
        pstm.setObject(4, text);
        return pstm.executeUpdate() > 0;
    }

    public static String getRoomDet(String patId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM RoomDetail WHERE patId=?");
        stm.setObject(1, patId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getString(2);

        } else {
            return "null";
        }
    }

    public static int getRomPrice(String room) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Room WHERE roomId=?");
        stm.setObject(1, room);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getInt(4);

        } else {
            return 0;
        }
    }

    public static String getRoomDetail(String patientId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM RoomDetail WHERE patId=?");
        stm.setObject(1, patientId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getString(2);

        } else {
            return "null";
        }
    }

    public static Room getRoomDeta(String roomId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Room WHERE roomId=?");
        stm.setObject(1, roomId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Room(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            );

        } else {
            return null;
        }
    }

    public List<String> getAllRoomIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Room WHERE Availability='"+"Available"+"'").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

}
