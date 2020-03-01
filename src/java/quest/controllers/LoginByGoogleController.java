/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quest.controllers;

import daos.OrderDAO;
import daos.RoleDAO;
import daos.UserDAO;
import dtos.GooglePojo;
import dtos.OrderDTO;
import dtos.RoleDTO;
import dtos.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.GoogleUtils;

/**
 *
 * @author ngochuu
 */
public class LoginByGoogleController extends HttpServlet {

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
            String code = request.getParameter("code");
            if (code == null || code.isEmpty()) {
                url = INVALID;
            } else {
                String accessToken = GoogleUtils.getToken(code);
                GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
                
                UserDTO userDTO = new UserDAO().findingAccountByGoogle(googlePojo.getEmail());
                if (userDTO == null) {
                    userDTO = new UserDTO();
                    userDTO.setUsername(googlePojo.getEmail());
                    userDTO.setFullname(googlePojo.getName());
                    userDTO.setRoleID(1);
                    if (!new UserDAO().storeByGoogleAccount(userDTO)) {
                        request.setAttribute("ERROR", "Login by Google Account failed");
                    }
                }

                RoleDTO roleDTO = new RoleDAO().getObjectByID(userDTO.getRoleID());
                if (roleDTO != null) {
                    HttpSession session = request.getSession();
                    if (roleDTO.getRoleName().equals("user")) {
                        OrderDTO orderDTO = new OrderDAO().getCurrentObjectByUsername(userDTO.getUsername());
                        if (orderDTO == null) {
                            orderDTO = new OrderDAO().createObject(userDTO.getUsername());
                        }
                        session.setAttribute("ORDER", orderDTO);
                    }
                    session.setAttribute("USER", userDTO);
                    session.setAttribute("ROLE", roleDTO);
                    url = SUCCESS;
                }
            }
        } catch (Exception e) {
            log("ERROR at LoginByGoogleController: " + e.getMessage());
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
