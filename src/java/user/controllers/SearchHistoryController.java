/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.controllers;

import daos.OrderDAO;
import daos.OrderDetailsDAO;
import daos.ProductDAO;
import dtos.OrderDTO;
import dtos.OrderDetailsDTO;
import dtos.ProductDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.util.ArrayList;
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
public class SearchHistoryController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "history.jsp";

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
            UserDTO userDTO = (UserDTO) session.getAttribute("USER");
            String txtSearch = request.getParameter("txtSearch");
            String dateStr = request.getParameter("dateStr");

            if (txtSearch == null) {
                txtSearch = "";
            }

            if (dateStr == null) {
                dateStr = "";
            }

            if (!dateStr.isEmpty()) {
                String[] temp = dateStr.split("/");
                dateStr = temp[2] + "-" + temp[0] + "-" + temp[1];
            }
            
            List<OrderDTO> listOrder = new OrderDAO().getHistoryByDate(dateStr, userDTO.getUsername());
            if (listOrder != null) {
                OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
                ProductDAO productDAO = new ProductDAO();
                //Hastable stores information of transaction hictory
                Hashtable<Integer, List<OrderDetailsDTO>> hashtableOrderDetails = new Hashtable<>();
                //Hastable stores information of product
                Hashtable<Integer, ProductDTO> hashtableProduct = new Hashtable<>();

                for (OrderDTO orderDTO : listOrder) {
                    //Get all products ID of a order
                    List<OrderDetailsDTO> listOrderDetailsDTOs = orderDetailsDAO.getObjectsByOrderID(orderDTO.getOrderID());
                    if (listOrderDetailsDTOs != null) {
                        //get all infomation by productID
                        hashtableOrderDetails.put(orderDTO.getOrderID(), listOrderDetailsDTOs);
                        for (OrderDetailsDTO listOrderDetailsDTO : listOrderDetailsDTOs) {
                            ProductDTO productDTO = productDAO.getObjectByProductID(listOrderDetailsDTO.getProductID());
                            if (productDTO.getProductName().contains(txtSearch)) {
                                hashtableProduct.put(productDTO.getProductID(), productDTO);
                            }
                        }
                    }
                }
                request.setAttribute("LIST_ORDER", listOrder);
                request.setAttribute("HASTABLE_ORDER_DETAILS", hashtableOrderDetails);
                request.setAttribute("HASTABLE_PRODUCT", hashtableProduct);
            } 
            url = SUCCESS;
        } catch (Exception e) {
            log("ERROR at SearchHistoryController: " + e.getMessage());
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
