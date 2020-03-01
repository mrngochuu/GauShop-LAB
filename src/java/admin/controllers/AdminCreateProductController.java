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
public class AdminCreateProductController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "AdminSearchProductController";
    private static final String INVALID = "creatingProduct.jsp";

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
            String productName = request.getParameter("productName");
            String description = request.getParameter("description");
            String imgURL = request.getParameter("imgURL");
            String priceStr = request.getParameter("price");
            String quantityStr = request.getParameter("quantity");
            String categoryStr = request.getParameter("category");

            ProductErrorObject errorObject = new ProductErrorObject();
            boolean validate = true;
            if (productName.length() <= 0 || productName.length() > 50) {
                errorObject.setProductNameError("Product name includes 1 - 50 characters!");
                validate = false;
            }

            if (description.length() <= 0 || description.length() > 200) {
                errorObject.setDescriptionError("Product description includes 1 - 200 characters!");
                validate = false;
            }

            if (imgURL.isEmpty()) {
                errorObject.setImgURLError("Product img is required!");
                validate = false;
            }

            float price = 0;
            try {
                price = Float.parseFloat(priceStr);
                if (price <= 0) {
                    throw new Exception();
                }
            } catch (Exception e) {
                errorObject.setPriceError("Product price is greater than 0$!");
                validate = false;
            }
            int quantity = 0;
            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity < 0) {
                    throw new Exception();
                }
            } catch (Exception e) {
                errorObject.setQuantityError("Product quantity is greater or equal to 0$!");
                validate = false;
            }

            int categoryID = 0;
            try {
                categoryID = Integer.parseInt(categoryStr);
                if (categoryID <= 0) {
                    throw new Exception();
                }
            } catch (Exception e) {
                errorObject.setCategoryError("Product category must be chosen!");
                validate = false;
            }

            if (validate) {
                ProductDTO dto = new ProductDTO();
                dto.setProductName(productName);
                dto.setDescription(description);
                dto.setImgURL(imgURL);
                dto.setPrice(price);
                dto.setQuantity(quantity);
                dto.setCategoryID(categoryID);
                dto.setStatus("active");

                if (new ProductDAO().createProduct(dto)) {
                    request.setAttribute("ERROR", "Creating Product is successful!");
                    url = SUCCESS;
                } else {
                    request.setAttribute("ERROR", "Creating Product is failed!");
                }
            } else {
                request.setAttribute("INVALID", errorObject);
                request.setAttribute("imgURL", imgURL);
                url = INVALID;
            }
        } catch (Exception e) {
            log("ERROR at AdinCreateProductController: " + e.getMessage());
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
