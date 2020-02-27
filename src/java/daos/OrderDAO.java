/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.OrderDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseUtils;

/**
 *
 * @author ngochuu
 */
public class OrderDAO implements Serializable {

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

    public OrderDTO getCurrentObjectByUsername(String username) throws SQLException, ClassNotFoundException {
        OrderDTO dto = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT OrderID FROM Orders WHERE username = ? AND isCheckout = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, username);
                pstm.setBoolean(2, false);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    dto = new OrderDTO();
                    dto.setOrderID(rs.getInt("OrderID"));
                    dto.setUsername(username);
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public OrderDTO createObject(String username) throws ClassNotFoundException, SQLException {
        OrderDTO dto = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO Orders (isCheckout, Username) VALUES(?,?)";
                String generatedColumns[] = {"OrderID"};
                pstm = conn.prepareStatement(sql, generatedColumns);
                pstm.setBoolean(1, false);
                pstm.setString(2, username);
                if (pstm.executeUpdate() > 0) {
                    rs = pstm.getGeneratedKeys();
                    if(rs.next()) {
                        dto = new OrderDTO();
                        dto.setUsername(username);
                        dto.setOrderID(rs.getInt(1));
                    }
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
    
    public boolean updateObject(OrderDTO dto) throws SQLException, ClassNotFoundException {
        boolean checked = false;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "UPDATE Orders SET isCheckout = ?, OrderPhone = ?, OrderAddress = ?, PaymentType = ?, RecipientName = ?, checkoutDate = ? WHERE orderID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setBoolean(1, dto.isCheckout());
                pstm.setString(2, dto.getOrderPhone());
                pstm.setString(3, dto.getOrderAddress());
                pstm.setString(4, dto.getPaymentType());
                pstm.setString(5, dto.getRecipientName());
                pstm.setTimestamp(6, dto.getCheckoutDate());
                pstm.setInt(7, dto.getOrderID());
                checked = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return checked;
    }
    
    public List<OrderDTO> getListObjectByUsername(String username, boolean isCheckout) throws SQLException, ClassNotFoundException {
        List<OrderDTO> list = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT OrderID, OrderPhone, OrderAddress, PaymentType, RecipientName, CheckoutDate FROM Orders WHERE username = ? AND isCheckout = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, username);
                pstm.setBoolean(2, isCheckout);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    if(list == null) {
                        list = new ArrayList<>();
                    }
                    OrderDTO dto = new OrderDTO();
                    dto.setOrderID(rs.getInt("OrderID"));
                    dto.setOrderPhone(rs.getString("OrderPhone"));
                    dto.setOrderAddress(rs.getString("OrderAddress"));
                    dto.setPaymentType(rs.getString("PaymentType"));
                    dto.setRecipientName(rs.getString("RecipentName"));
                    dto.setCheckoutDate(rs.getTimestamp("CheckoutDate"));
                    dto.setUsername(username);
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }
}
