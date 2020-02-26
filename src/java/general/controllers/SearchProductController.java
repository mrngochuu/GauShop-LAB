/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general.controllers;

import daos.CategoryDAO;
import daos.ProductDAO;
import dtos.CategoryDTO;
import dtos.ProductDTO;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ngochuu
 */
public class SearchProductController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "index.jsp";

    private static final int ROWS_PER_PAGE = 20;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            Hashtable<Integer,CategoryDTO> listCategory =  (Hashtable<Integer,CategoryDTO>) session.getAttribute("LIST_CATEGORY");
            if (listCategory == null) {
                listCategory = new CategoryDAO().getList();
                session.setAttribute("LIST_CATEGORY", listCategory);
            }

            String txtSearch = request.getParameter("txtSearch");
            String cbRange = request.getParameter("cbRange");
            String cbCategory = request.getParameter("cbCategory");
            String pageStr = request.getParameter("pageStr");

            if (txtSearch == null) {
                txtSearch = "";
            }
            int lowerBound = 0, upperBound = 0;

            if (cbRange != null && !cbRange.isEmpty()) {
                String[] tempStrings = cbRange.split("-");
                lowerBound = Integer.parseInt(tempStrings[0]);
                if (tempStrings.length == 2) {
                    upperBound = Integer.parseInt(tempStrings[1]);
                }
            }
            
            if(cbCategory == null || cbCategory.isEmpty()) {
                cbCategory = "0";
            }

            if (pageStr == null) {
                pageStr = "1";
            }
            
            ProductDAO productDAO = new ProductDAO();
            int totalRecords = productDAO.getTotalRecord(txtSearch, lowerBound, upperBound, Integer.parseInt(cbCategory), "active", 1);

            if (totalRecords > 0) {
                //set Total page
                int totalPage = totalRecords / ROWS_PER_PAGE;
                if (totalRecords % ROWS_PER_PAGE != 0) {
                    totalPage++;
                }
                request.setAttribute("TOTAL_PAGE", totalPage);

                int pageNum;
                try {
                    pageNum = Integer.parseInt(pageStr);
                    if (pageNum <= 0 || pageNum > totalPage) {
                        pageNum = 1;
                    }
                } catch (Exception e) {
                    pageNum = 1;
                }
                List<ProductDTO> listProduct = productDAO.getList(txtSearch, lowerBound, upperBound, Integer.parseInt(cbCategory), "active", 1, (pageNum - 1) * ROWS_PER_PAGE, ROWS_PER_PAGE);

                request.setAttribute("LIST_PRODUCT", listProduct);
            } else {
                request.setAttribute("TOTAL_PAGE", 1);
            }
            url = SUCCESS;
        } catch (Exception e) {
            log("ERROR at SearchProductController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
