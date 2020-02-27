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
public class ShowingHistoryController extends HttpServlet {

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

            List<OrderDTO> listOrder = new OrderDAO().getListObjectByUsername(userDTO.getUsername(), true);
            if (listOrder != null) {
                for (OrderDTO orderDTO : listOrder) {
                    List<OrderDetailsDTO> listOrderDetails = new OrderDetailsDAO().getObjectsByOrderID(orderDTO.getOrderID());
                    Hashtable<Integer, ProductDTO> listProduct = new Hashtable<>();
                    for (OrderDetailsDTO orderDetails : listOrderDetails) {
                        listProduct.put(orderDetails.getProductID(), new ProductDAO().getObjectByProductID(orderDetails.getProductID()));
                    }
                    request.setAttribute("LIST_ORDER_DETAILS_" + orderDTO.getOrderID(), listOrderDetails);
                    request.setAttribute("LIST_PRODUCT_" + orderDTO.getOrderID(), listProduct);
                }
            }
            request.setAttribute("LIST_ORDER", listOrder);
            url = SUCCESS;
        } catch (Exception e) {
            log("ERROR at ShowingHistoryController: " + e.getMessage());
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
