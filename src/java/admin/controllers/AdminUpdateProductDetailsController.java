/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import daos.ProductDAO;
import dtos.ProductDTO;
import dtos.ProductErrorObject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ngochuu
 */
public class AdminUpdateProductDetailsController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "AdminSearchProductController";
    private static final String INVALID = "AdminShowProductController";

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
            int productID;
            try {
                productID = Integer.parseInt(request.getParameter("productID"));
            } catch (Exception e) {
                productID = 0;
            }
            String productName = request.getParameter("productName");
            String imgURL = request.getParameter("imgURL");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");
            String quantityStr = request.getParameter("quantity");
            String status = request.getParameter("status");
            String categoryStr = request.getParameter("category");
            
            ProductErrorObject errorObject = new ProductErrorObject();
            boolean validate = true;
            if(productName.isEmpty()){
                errorObject.setProductNameError("Product name is required!");
                validate = false;
            }
            
            if(imgURL.isEmpty()) {
                errorObject.setImgURLError("Product img is required");
                validate = false;
            }
            
            if(description.isEmpty()) {
                errorObject.setDescriptionError("Product description is required!");
                validate = false;
            }
            float price = 0;
            try {
                price = Float.parseFloat(priceStr);
                if(price <= 0) {
                    throw new Exception();
                }
            } catch (Exception e) {
                errorObject.setPriceError("Product price is greater than 0$!");
                validate = false;
            }
            
            int quantity = 0;
            try {
                quantity = Integer.parseInt(quantityStr);
                if(quantity < 0) {
                    throw new Exception();
                }
            } catch (Exception e) {
                errorObject.setQuantityError("Product quantity is greater or equal to 0!");
                validate = false;
            }
            
            if(status.isEmpty()) {
                errorObject.setStatusError("Product status must be chosen!");
                validate = false;
            }
            
            int categoryID = 0;
            try {
                categoryID = Integer.parseInt(categoryStr);
                if(categoryID <= 0) {
                    throw new Exception();
                }
            } catch (Exception e) {
                errorObject.setCategoryError("Product category must be chosen!");
                validate = false;
            }
            
            if(validate) {
                if(productID > 0) {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setProductID(productID);
                    productDTO.setProductName(productName);
                    productDTO.setImgURL(imgURL);
                    productDTO.setDescription(description);
                    productDTO.setPrice(price);
                    productDTO.setQuantity(quantity);
                    productDTO.setStatus(status);
                    productDTO.setCategoryID(categoryID);
                    if(new ProductDAO().updateDetails(productDTO)) {
                        request.setAttribute("MESSAGE", "Updating product is succesful!");
                        url = SUCCESS;
                    } else {
                        request.setAttribute("ERROR", "Updating product is failed!");
                    }
                } else {
                    request.setAttribute("ERROR", "Product does not exist!");
                }
            } else {
                request.setAttribute("INVALID", errorObject);
                url = INVALID;
            }
        } catch (Exception e) {
            log("ERROR at AdminUpdateProductDetailsController: " + e.getMessage());
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
