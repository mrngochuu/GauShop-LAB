/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ngochuu
 */
public class MainController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String LOGIN = "LoginController";
    private static final String SEARCHING_PRODUCT = "SearchProductController";
    private static final String REGISTRATION = "RegistrationController";
    private static final String ADDING_PRODUCT = "AddingProductController";
    private static final String SHOWING_CART = "ShowingCartController";
    private static final String UPDATING_QUANTITY = "UpdatingQuantityController";
    private static final String DELETING_FROM_CART = "DeleteFromCartController";
    private static final String PAYMENT = "PaymentController";
    private static final String SHOWING_HISTORY = "ShowingHistoryController";
    
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
            String action = request.getParameter("action");
            if(action.equals("Login")) {
                url = LOGIN;
            } else if(action.equals("SearchProduct")) {
                url = SEARCHING_PRODUCT;
            } else if(action.equals("AddingProduct")) {
                url = ADDING_PRODUCT;
            } else if(action.equals("Register")) {
                url = REGISTRATION;
            } else if(action.equals("ShowCart")) {
                url = SHOWING_CART;
            } else if(action.equals("UpdateQuantity")) {
                url = UPDATING_QUANTITY;
            } else if(action.equals("DeleteFromCart")) {
                url = DELETING_FROM_CART;
            } else if(action.equals("Pay")) {
                url = PAYMENT;
            } else if(action.equals("ShowHistory")) {
                url = SHOWING_HISTORY;
            } else {
                request.setAttribute("ERROR", "The action is not found!");
            }
        } catch (Exception e) {
            log("ERROR at MainController: " + e.getMessage());
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
