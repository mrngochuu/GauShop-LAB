/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import dtos.ProductDTO;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author ngochuu
 */
public class EditImageController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "productDetails.jsp";

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
        String url = SUCCESS;
        try {
            boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
            if (isMultipartContent) {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> fields = upload.parseRequest(request);
                Iterator<FileItem> it = fields.iterator();
                if (it.hasNext()) {
                    FileItem fileItem = it.next();
                    boolean isFormField = fileItem.isFormField();
                    if (!isFormField) {
                        if (fileItem.getSize() > 0) {
                            fileItem.write(new File("/Library/Tomcat/webapps/Image/" + fileItem.getName()));
                            url = SUCCESS;
                        }

                        String productID = it.next().getString();
                        String productName = it.next().getString();
                        String description = it.next().getString();
                        String price = it.next().getString();
                        String quantity = it.next().getString();
                        String category = it.next().getString();
                        String status = it.next().getString();
                        String postingDate = it.next().getString();

                        ProductDTO productDTO = new ProductDTO();
                        productDTO.setProductID(Integer.parseInt(productID));
                        productDTO.setProductName(productName);
                        productDTO.setDescription(description);
                        productDTO.setPrice(Float.parseFloat(price));
                        productDTO.setQuantity(Integer.parseInt(quantity));
                        productDTO.setCategoryID(Integer.parseInt(category));
                        productDTO.setStatus(status);
                        productDTO.setImgURL(fileItem.getName());
                        productDTO.setPostingDate(Timestamp.valueOf(postingDate));

                        request.setAttribute("PRODUCT", productDTO);
                    } else {
                        request.setAttribute("ERROR", "Upload file failed!");
                    }
                }
            } else {
                request.setAttribute("ERROR", "Upload file failed!");
            }
        } catch (Exception e) {
            log("ERROR at UploadImageController" + e.getMessage());
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
