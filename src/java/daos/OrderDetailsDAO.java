/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.OrderDetailsDTO;
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
public class OrderDetailsDAO implements Serializable {

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
    
    public boolean checkProductExist(int orderID, int productID) throws ClassNotFoundException, SQLException {
        boolean checked = true;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT orderID FROM OrderDetails WHERE orderID = ? AND productID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, orderID);
                pstm.setInt(2, productID);
                rs = pstm.executeQuery();
                checked = rs.next();
            }
        } finally {
            closeConnection();
        }
        return checked;
    }
    
    public boolean addProduct(int orderID, int productID, double price) throws ClassNotFoundException, SQLException {
        boolean checked = true;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO OrderDetails (orderID, productID, price, quantity) VALUES (?,?,?,?)";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, orderID);
                pstm.setInt(2, productID);
                pstm.setDouble(3, price);
                pstm.setInt(4, 1);
                checked = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return checked;
    }
    
    public List<OrderDetailsDTO> getObjectsByOrderID(int orderID) throws SQLException, ClassNotFoundException {
        List<OrderDetailsDTO> list = null;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "SELECT productID, quantity, price FROM OrderDetails WHERE orderID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, orderID);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    if(list == null) {
                        list = new ArrayList<>();
                    }
                    OrderDetailsDTO dto = new OrderDetailsDTO();
                    dto.setOrderID(orderID);
                    dto.setProductID(rs.getInt("productID"));
                    dto.setQuantity(rs.getInt("quantity"));
                    dto.setPrice(rs.getFloat("price"));
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    
    public boolean updateQuantity(int orderID, int productID, int quantity) throws SQLException, ClassNotFoundException {
        boolean checked = false;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "UPDATE OrderDetails SET quantity = ? WHERE orderID = ? AND productID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, quantity);
                pstm.setInt(2, orderID);
                pstm.setInt(3, productID);
                checked = pstm.executeUpdate() > 0;
            }
        }finally {
            closeConnection();
        }
        return checked;
    }
    
    public boolean deleteFromCart(int orderID, int productID) throws ClassNotFoundException, SQLException {
        boolean checked = false;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "DELETE FROM OrderDetails WHERE orderID = ? AND productID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, orderID);
                pstm.setInt(2, productID);
                checked = pstm.executeUpdate() > 0;
            }
        }finally {
            closeConnection();
        }
        return checked;
    }
}
