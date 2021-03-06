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
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
                String sql = "SELECT ProductID, ProductName, Price, Quantity, ImgURL, Status, CategoryID, postingDate, description, quantity FROM Products WHERE productName LIKE ? AND quantity >= ? ";

                if (lowerBound != 0) {
                    sql += "AND price >= " + lowerBound + " ";
                }

                if (upperBound != 0) {
                    sql += "AND price <= " + upperBound + " ";
                }

                if (categoryID != 0) {
                    sql += "AND CategoryID = " + categoryID + " ";
                }

                if (!status.isEmpty()) {
                    sql += "AND status = '" + status + "'";
                }

                sql += "ORDER BY postingDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, "%" + txtSearch + "%");
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
                    dto.setQuantity(rs.getInt("quantity"));
                    dto.setStatus(rs.getString("Status"));
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
                String sql = "SELECT COUNT(ProductID) AS total FROM Products WHERE productName LIKE ? AND quantity >= ? ";

                if (lowerBound != 0) {
                    sql += "AND price >= " + lowerBound + " ";
                }

                if (upperBound != 0) {
                    sql += "AND price <= " + upperBound + " ";
                }

                if (categoryID != 0) {
                    sql += "AND CategoryID = " + categoryID + " ";
                }

                if (!status.isEmpty()) {
                    sql += "AND status = '" + status + "'";
                }

                pstm = conn.prepareStatement(sql);
                pstm.setString(1, "%" + txtSearch + "%");
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
                String sql = "SELECT productName, description, imgURL, price, quantity, status, categoryID, postingDate  FROM products WHERE productID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, productID);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    dto = new ProductDTO();
                    dto.setProductID(productID);
                    dto.setProductName(rs.getString("productName"));
                    dto.setDescription(rs.getString("description"));
                    dto.setImgURL(rs.getString("imgURL"));
                    dto.setStatus(rs.getString("status"));
                    dto.setQuantity(rs.getInt("quantity"));
                    dto.setPrice(rs.getFloat("price"));
                    dto.setCategoryID(rs.getInt("categoryID"));
                    dto.setPostingDate(rs.getTimestamp("postingDate"));
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public boolean checkAvaiable(int productID, int quantity, String status) throws ClassNotFoundException, SQLException {
        boolean checked = false;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT productID FROM products WHERE productID = ? AND quantity >= ? AND status = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, productID);
                pstm.setInt(2, quantity);
                pstm.setString(3, status);
                rs = pstm.executeQuery();
                checked = rs.next();
            }
        } finally {
            closeConnection();
        }
        return checked;
    }

    public void updateQuantity(int productID, int quantity) throws ClassNotFoundException, SQLException {
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE Products SET quantity = ? WHERE productID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, quantity);
                pstm.setInt(2, productID);
                pstm.executeUpdate();
            }
        } finally {
            closeConnection();
        }
    }

    public boolean updateCategoryAndStatus(int productID, String status, int categoryID) throws SQLException, ClassNotFoundException {
        boolean checked = false;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE Products SET status = ?, categoryID = ?, postingDate = ? WHERE productID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, status);
                pstm.setInt(2, categoryID);
                pstm.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                pstm.setInt(4, productID);
                checked = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return true;
    }

    public boolean updateDetails(ProductDTO dto) throws SQLException, ClassNotFoundException {
        boolean checked = false;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE Products SET productName = ?, imgURL = ?, description = ?, price = ?, quantity = ?, status = ?, categoryID = ?, postingDate = ? WHERE productID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, dto.getProductName());
                pstm.setString(2, dto.getImgURL());
                pstm.setString(3, dto.getDescription());
                pstm.setFloat(4, dto.getPrice());
                pstm.setInt(5, dto.getQuantity());
                pstm.setString(6, dto.getStatus());
                pstm.setInt(7, dto.getCategoryID());
                pstm.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                pstm.setInt(9, dto.getProductID());
                checked = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return true;
    }

    public boolean createProduct(ProductDTO dto) throws SQLException, ClassNotFoundException {
        boolean checked = false;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO Products (productName, imgURL, description, price, quantity, status, categoryID, postingDate) VALUES (?,?,?,?,?,?,?,?)";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, dto.getProductName());
                pstm.setString(2, dto.getImgURL());
                pstm.setString(3, dto.getDescription());
                pstm.setFloat(4, dto.getPrice());
                pstm.setInt(5, dto.getQuantity());
                pstm.setString(6, dto.getStatus());
                pstm.setInt(7, dto.getCategoryID());
                pstm.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                checked = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return true;
    }
    
}
