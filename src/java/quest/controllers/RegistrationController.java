/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quest.controllers;

import daos.RoleDAO;
import daos.UserDAO;
import dtos.RoleDTO;
import dtos.UserDTO;
import dtos.UserErrorObject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ngochuu
 */
public class RegistrationController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "login.jsp";
    private static final String INVALID = "registration.jsp";

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
            String fullname = request.getParameter("txtFullname");
            String password = request.getParameter("txtPassword");
            String confirm = request.getParameter("txtConfirm");
            String phone = request.getParameter("txtPhone");
            String address = request.getParameter("txtAddress");
            
            
            UserErrorObject errorObject = new UserErrorObject();
            boolean validate = true;
            if (username.isEmpty()) {
                errorObject.setUsernameError("Username is required!");
                validate = false;
            } else {
                if (new UserDAO().checkAccountExisted(username)) {
                    errorObject.setDuplicationError("Username is existed!");
                    validate = false;
                }

                if (username.length() > 20) {
                    errorObject.setUsernameError("Max length of username is 20 characters!");
                    validate = false;
                }
            }

            if (fullname.length() <= 0 || fullname.length() > 50) {
                errorObject.setFullnameError("Length of fullname is from 1 to 50 characters!");
                validate = false;
            }

            if (password.length() <= 0 || password.length() > 20) {
                errorObject.setPasswordError("Length of password is from 1 to 50 characters!");
                validate = false;
            } else {
                if (!confirm.equals(password)) {
                    errorObject.setConfirmError("Retype password is not match!");
                    validate = false;
                }
            }

            if (!phone.matches("0\\d{2}-\\d{7}")) {
                errorObject.setPhoneError("Form Phone Number: 0XX-XXXXXXX, X is stand for digits!");
                validate = false;
            }

            if (address.isEmpty()) {
                errorObject.setAddressError("Address is required!");
                validate = false;
            }

            if (validate) {
                RoleDTO roleDTO = new RoleDAO().getObjectByName("user");
                if (roleDTO != null) {
                    UserDTO dto = new UserDTO();
                    dto.setUsername(username);
                    dto.setFullname(fullname);
                    dto.setPassword(password);
                    dto.setPhone(phone);
                    dto.setAddress(address);
                    dto.setRoleID(roleDTO.getRoleID());
                    if (new UserDAO().storeAccount(dto)) {
                        request.setAttribute("MESSAGE", "Register is successful!");
                        url = SUCCESS;
                    }
                } else {
                    request.setAttribute("ERROR", "Role is not found!");
                }
            } else {
                request.setAttribute("INVALID", errorObject);
                url = INVALID;
            }
        } catch (Exception e) {
            log("ERROR at RegistrationController: " + e.getMessage());
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
