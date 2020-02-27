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
import dtos.PaymentErrorObject;
import dtos.ProductDTO;
import java.io.IOException;
import java.sql.Timestamp;
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
public class PaymentController extends HttpServlet {
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "SearchProductController";
    private static final String INVALID_PRODUCT = "ShowingCartController";
    private static final String INVALID = "orderDetails.jsp";
    
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
            String name = request.getParameter("txtRecipientName");
            String phone = request.getParameter("txtPhone");
            String address = request.getParameter("txtAddress");
            String payment = request.getParameter("paymentType");
            PaymentErrorObject errorObject = new PaymentErrorObject();
            boolean validate = true;
            if(name.isEmpty()) {
                errorObject.setRecipientNameError("Recipent name is required!");
                validate = false;
            }
            
            if(!phone.matches("0\\d{2}-\\d{7}")) {
                errorObject.setPhoneError("Form phone number is 0XX-XXXXXXX (X is stand for digits)!");
                validate = false;
            }
            
            if(address.isEmpty()) {
                errorObject.setAddressError("Address is required!");
                validate = false;
            }
            
            if(payment.isEmpty()) {
                errorObject.setPaymentTypeError("Please choose a payment type!");
                validate = false;
            }
            if(validate) {
                List<OrderDetailsDTO> listOrderDetails = new OrderDetailsDAO().getObjectsByOrderID(orderDTO.getOrderID());
                if(listOrderDetails != null) {
                    boolean checkedSoldout = true;
                    ProductDAO productDAO = new ProductDAO();
                    //check avaiable products
                    for (OrderDetailsDTO dto : listOrderDetails) {
                        if(!productDAO.checkAvaiable(dto.getOrderID(), dto.getQuantity(), "active")) {
                            checkedSoldout = false;
                            break;
                        }
                    }
                    if(checkedSoldout) {
                        orderDTO.setOrderAddress(address);
                        orderDTO.setOrderPhone(phone);
                        orderDTO.setRecipientName(name);
                        orderDTO.setPaymentType(payment);
                        orderDTO.setCheckout(true);
                        orderDTO.setCheckoutDate(Timestamp.valueOf(java.time.LocalDateTime.now()));
                        
                        if(new OrderDAO().updateObject(orderDTO)) {
                            //subtract quantity in store
                            for (OrderDetailsDTO dto : listOrderDetails) {
                                String status = "";
                                ProductDTO productDTO = productDAO.getObjectByProductID(dto.getProductID());
                                if(productDTO.getQuantity() == dto.getQuantity()) {
                                    status = "soldout";
                                }
                                productDAO.updateQuantity(productDTO.getProductID(), productDTO.getQuantity() - dto.getQuantity(), status);
                            }
                            OrderDTO newOrderDTO = new OrderDAO().createObject(orderDTO.getUsername());
                            session.setAttribute("ORDER", newOrderDTO);
                            request.setAttribute("MESSAGE", "Payment is successful!");
                            url = SUCCESS;
                        } else {
                            request.setAttribute("ERROR", "Payment is failled!");
                        }
                    } else {
                        request.setAttribute("MESSAGE", "Please delete unavaiable products or update the quantity to payment!");
                        url = INVALID_PRODUCT;
                    }
                } else {
                    request.setAttribute("ERROR", "Find the products failed!");
                }
            } else {
                request.setAttribute("INVALID", errorObject);
                url = INVALID;
            }
        } catch (Exception e) {
            log("ERROR at PaymentController: " + e.getMessage());
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
