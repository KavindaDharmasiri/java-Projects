package controller;

import DBConnection.DBConnection;
import javafx.scene.control.TextField;
import module.loginDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageLogin {

    public static boolean addNewUser(String value, TextField txtUserName, String txtPassword) throws SQLException, ClassNotFoundException {

        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO `loginDetail` VALUE (?,?,?)");
        pstm.setObject(1, txtUserName.getText());
        pstm.setObject(2, txtPassword);
        pstm.setObject(3, value);

        return pstm.executeUpdate() > 0;
    }

    public static List<loginDetail> searchuserName(String txtUserName) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM `loginDetail` WHERE userName = '" + txtUserName + "'");
        ResultSet rs = pstm.executeQuery();

        List<loginDetail> detail = new ArrayList<>();

        while (rs.next()) {
            detail.add(new loginDetail(
                    rs.getString("userName"),
                    rs.getString("password"),
                    rs.getString("type")
            ));
        }

        return detail;

    }

    public static List<loginDetail> getDetail(TextField userName, String admin) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM `loginDetail` WHERE type LIKE '%" + admin + "%'");
        ResultSet rs = pstm.executeQuery();

        List<loginDetail> detail = new ArrayList<>();

        while (rs.next()) {
            detail.add(new loginDetail(
                    rs.getString("userName"),
                    rs.getString("password"),
                    rs.getString("type")
            ));
        }

        return detail;
    }
}
