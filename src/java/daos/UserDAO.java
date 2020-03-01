/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.UserDTO;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DatabaseUtils;
import utils.PasswordUtils;

/**
 *
 * @author ngochuu
 */
public class UserDAO implements Serializable {

    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public UserDTO checkLogin(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        UserDTO dto = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT Fullname, Phone, Address, RoleID FROM Users WHERE username = ? AND password = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, username);
                pstm.setString(2, PasswordUtils.hashPassword(password));
                rs = pstm.executeQuery();
                if (rs.next()) {
                    dto = new UserDTO();
                    dto.setUsername(username);
                    dto.setFullname(rs.getString("Fullname"));
                    dto.setPhone(rs.getString("Phone"));
                    dto.setAddress(rs.getString("Address"));
                    dto.setRoleID(rs.getInt("RoleID"));
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public boolean checkAccountExisted(String username) throws ClassNotFoundException, SQLException {
        boolean checked = true;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT username FROM Users WHERE username = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, username);
                rs = pstm.executeQuery();
                checked = rs.next();
            }
        } finally {
            closeConnection();
        }
        return checked;
    }

    public boolean storeAccount(UserDTO dto) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        boolean checked = true;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO Users VALUES(?,?,?,?,?,?)";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, dto.getUsername());
                pstm.setString(2, PasswordUtils.hashPassword(dto.getPassword()));
                pstm.setString(3, dto.getFullname());
                pstm.setString(4, dto.getPhone());
                pstm.setString(5, dto.getAddress());
                pstm.setInt(6, dto.getRoleID());
                checked = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return checked;
    }

    public UserDTO findingAccountByGoogle(String username) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        UserDTO dto = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT Fullname, Phone, Address, RoleID FROM Users WHERE username = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, username);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    dto = new UserDTO();
                    dto.setUsername(username);
                    dto.setFullname(rs.getString("Fullname"));
                    dto.setPhone(rs.getString("Phone"));
                    dto.setAddress(rs.getString("Address"));
                    dto.setRoleID(rs.getInt("RoleID"));
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
    
    public boolean storeByGoogleAccount(UserDTO dto) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        boolean checked = true;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO Users (username, fullname, roleID) VALUES (?,?,?)";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, dto.getUsername());
                pstm.setString(2, dto.getFullname());
                pstm.setInt(3, dto.getRoleID());
                checked = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return checked;
    }
}
