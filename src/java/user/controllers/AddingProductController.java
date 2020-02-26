/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.controllers;

import daos.OrderDetailsDAO;
import dtos.OrderDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ngochuu
 */
public class AddingProductController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "SearchProductController";

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
            OrderDTO orderDTO = (OrderDTO) session.getAttribute("ORDER");

            String productStr = request.getParameter("productID");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            double price = Double.parseDouble(request.getParameter("price"));
            if (quantity > 0) {
                int productID = Integer.parseInt(productStr);
                if (productID > 0) {
                    if (!(new OrderDetailsDAO().checkProductExist(orderDTO.getOrderID(), productID))) {
                        if(new OrderDetailsDAO().addProduct(orderDTO.getOrderID(), productID, price)) {
                            request.setAttribute("MESSAGE", "Adding product to cart successful!");
                            url = SUCCESS;
                        } else {
                            request.setAttribute("ERROR", "Adding product failed!");
                        }
                    } else {
                        request.setAttribute("MESSAGE", "Product already added!");
                        url = SUCCESS;
                    }
                } else {
                    request.setAttribute("ERROR", "Product is not found!");
                }
            } else {
                request.setAttribute("MESSAGE", "Sorry, product sold out!");
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("ERROR at AddingProductController: " + e.getMessage());
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
