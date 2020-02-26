/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.ProductDTO;
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
public class ProductDAO implements Serializable {

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

    public List<ProductDTO> getList(String txtSearch, int lowerBound, int upperBound, int categoryID, String status, int quantity, int startedPage, int rowsInPage) throws SQLException, ClassNotFoundException {
        List<ProductDTO> list = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT ProductID, ProductName, Price, Quantity, ImgURL, Status, CategoryID, postingDate, description FROM Products WHERE status = ? AND quantity >= ? ";
                if (!txtSearch.isEmpty()) {
                    sql += "AND productName LIKE '%" + txtSearch + "%' ";
                }

                if (lowerBound != 0) {
                    sql += "AND price >= " + lowerBound + " ";
                }

                if (upperBound != 0) {
                    sql += "AND price <= " + upperBound + " ";
                }

                if (categoryID != 0) {
                    sql += "AND CategoryID = " + categoryID + " ";
                }

                sql += "ORDER BY postingDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, status);
                pstm.setInt(2, quantity);
                pstm.setInt(3, startedPage);
                pstm.setInt(4, rowsInPage);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    ProductDTO dto = new ProductDTO();
                    dto.setProductID(rs.getInt("ProductID"));
                    dto.setProductName(rs.getString("ProductName"));
                    dto.setPrice(rs.getFloat("Price"));
                    dto.setQuantity(rs.getInt("Quantity"));
                    dto.setImgURL(rs.getString("ImgURL"));
                    dto.setCategoryID(rs.getInt("CategoryID"));
                    dto.setPostingDate(rs.getTimestamp("postingDate"));
                    dto.setDescription(rs.getString("description"));
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public int getTotalRecord(String txtSearch, int lowerBound, int upperBound, int categoryID, String status, int quantity) throws SQLException, ClassNotFoundException {
        int total = 0;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(ProductID) AS total FROM Products WHERE status = ? AND quantity >= ? ";
                if (!txtSearch.isEmpty()) {
                    sql += "AND productName LIKE '%" + txtSearch + "%' ";
                }

                if (lowerBound != 0) {
                    sql += "AND price >= " + lowerBound + " ";
                }

                if (upperBound != 0) {
                    sql += "AND price <= " + upperBound + " ";
                }

                if (categoryID != 0) {
                    sql += "AND CategoryID = " + categoryID + " ";
                }

                pstm = conn.prepareStatement(sql);
                pstm.setString(1, status);
                pstm.setInt(2, quantity);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    total = rs.getInt("total");
                }
            }
        } finally {
            closeConnection();
        }
        return total;
    }

    public ProductDTO getObjectByProductID(int productID) throws SQLException, ClassNotFoundException {
        ProductDTO dto = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT productName, description, imgURL FROM products WHERE productID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, productID);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    dto = new ProductDTO();
                    dto.setProductID(productID);
                    dto.setProductName(rs.getString("productName"));
                    dto.setDescription(rs.getString("description"));
                    dto.setImgURL(rs.getString("imgURL"));
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
}
