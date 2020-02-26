/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.CategoryDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import utils.DatabaseUtils;

/**
 *
 * @author ngochuu
 */
public class CategoryDAO implements Serializable {

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

    public Hashtable<Integer,CategoryDTO> getList() throws SQLException, ClassNotFoundException {
        Hashtable<Integer,CategoryDTO> list = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT CategoryID, CategoryName FROM Categories";
                pstm = conn.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new Hashtable<>();
                    }
                    CategoryDTO dto = new CategoryDTO();
                    dto.setCategoryID(rs.getInt("CategoryID"));
                    dto.setCategoryName(rs.getString("CategoryName"));
                    list.put(dto.getCategoryID(),dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }
}
