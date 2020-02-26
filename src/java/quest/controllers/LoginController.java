/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quest.controllers;

import daos.OrderDAO;
import daos.RoleDAO;
import daos.UserDAO;
import dtos.OrderDTO;
import dtos.RoleDTO;
import dtos.UserDTO;
import dtos.UserErrorObject;
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
public class LoginController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "SearchProductController";
    private static final String INVALID = "login.jsp";

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
            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");

            UserErrorObject errorObj = new UserErrorObject();
            boolean valid = true;
            if (username.isEmpty() || username == null) {
                errorObj.setUsernameError("Username is required!");
                valid = false;
            }

            if (password.isEmpty() || username == null) {
                errorObj.setPasswordError("Password is required!");
                valid = false;
            }

            if (valid) {
                UserDTO userDTO = new UserDAO().checkLogin(username, password);
                if (userDTO != null) {
                    RoleDTO roleDTO = new RoleDAO().getObjectByID(userDTO.getRoleID());
                    if (roleDTO != null) {
                        OrderDTO orderDTO = new OrderDAO().getCurrentObjectByUsername(username);
                        if(orderDTO == null) {
                            orderDTO = new OrderDAO().createObject(username);
                        }
                        HttpSession session = request.getSession();
                        session.setAttribute("USER", userDTO);
                        session.setAttribute("ROLE", roleDTO);
                        session.setAttribute("ORDER", orderDTO);
                        url = SUCCESS;
                    } else {
                        request.setAttribute("ERROR", "Role is not found!");
                    }
                } else {
                    errorObj.setLoginError("Invalid username or password!");
                    request.setAttribute("INVALID", errorObj);
                    url = INVALID;
                }
            } else {
                request.setAttribute("INVALID", errorObj);
                url = INVALID;
            }

        } catch (Exception e) {
            log("ERROR at LoginController: " + e.getMessage());
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
