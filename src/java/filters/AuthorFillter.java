/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import dtos.RoleDTO;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ngochuu
 */
public class AuthorFillter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private final List<String> user;
    private final List<String> admin;
    private final List<String> quest;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public AuthorFillter() {
        quest = new ArrayList<>();
        quest.add("");
        quest.add("error.jsp");
        quest.add("index.jsp");
        quest.add("login.jsp");
        quest.add("registration.jsp");
        quest.add("footer.jsp");
        quest.add("header.jsp");
        quest.add("newjsp.jsp");

        quest.add("MainController");
        quest.add("SearchProductController");
        quest.add("LoginController");
        quest.add("RegistrationController");
        quest.add("LoginByGoogleController");
        quest.add("login-google");

        quest.add("SearchProduct");
        quest.add("Login");
        quest.add("Register");

        user = new ArrayList<>();
        user.add("");
        user.add("error.jsp");
        user.add("index.jsp");
        user.add("header.jsp");
        user.add("footer.jsp");
        user.add("cart.jsp");
        user.add("history.jsp");
        user.add("orderDetails.jsp");
        user.add("confirmation.jsp");

        user.add("MainController");
        user.add("SearchController");
        user.add("LogoutController");
        user.add("AddingProductController");
        user.add("DeleteFromCartController");
        user.add("PaymentController");
        user.add("SearchHistoryController");
        user.add("ShowingCartController");
        user.add("UpdatingQuantityController");

        user.add("SearchProduct");
        user.add("Logout");
        user.add("AddingProduct");
        user.add("DeleteFromCart");
        user.add("Pay");
        user.add("SearchHistory");
        user.add("ShowCart");
        user.add("UpdateQuantity");

        admin = new ArrayList<>();
        admin.add("");
        admin.add("admin.jsp");
        admin.add("confirmation.jsp");
        admin.add("error.jsp");
        admin.add("footer.jsp");
        admin.add("header.jsp");
        admin.add("index.jsp");
        admin.add("creatingProduct.jsp");
        admin.add("productDetails.jsp");

        admin.add("AdminCreateProductController");
        admin.add("AdminSearchProductController");
        admin.add("AdminShowProductController");
        admin.add("AdminUpdateProductController");
        admin.add("AdminUpdateProductDetailsController");
        admin.add("EditImageController");
        admin.add("UploadImageController");
        admin.add("LogoutController");
        admin.add("MainController");
        admin.add("SearchProductController");

        admin.add("AdminCreateProduct");
        admin.add("AdminSearchProduct");
        admin.add("AdminShowProduct");
        admin.add("AdminUpdateProduct");
        admin.add("AdminUpdateProductDetails");
        admin.add("Logout");
        admin.add("SearchProduct");
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("RoleFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("RoleFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        log("Uri: " + uri);
        if ((uri.contains(".js") || uri.contains(".css") || uri.contains(".jpg") || uri.contains(".png") || uri.contains(".gif") || uri.contains(".jpeg")) && !uri.contains(".jsp")) {
            chain.doFilter(request, response);
        } else {
            int index = uri.lastIndexOf("/");
            String resource = uri.substring(index + 1);
            HttpSession session = req.getSession(false);
            log("AuthenFilter: " + resource);
            String action = req.getParameter("action");
            if (action == null) {
                action = "";
            }
            if ((session == null) || session.getAttribute("ROLE") == null) {
                if (quest.contains(resource) && quest.contains(action)) {
                    chain.doFilter(request, response);
                } else {
                    res.sendRedirect("login.jsp");
                }
            } else {
                String role = ((RoleDTO) session.getAttribute("ROLE")).getRoleName();
                if (role.equals("admin")) {
                    if (admin.contains(resource) && admin.contains(action)) {
                        chain.doFilter(request, response);
                    } else {
                        req.getRequestDispatcher("AdminSearchProductController").forward(request, response);
                    }
                } else if (role.equals("user")) {
                    if (user.contains(resource) && user.contains(action)) {
                        chain.doFilter(request, response);
                    } else {
                        req.getRequestDispatcher("SearchProductController").forward(request, response);
                    }
                } else {
                    req.getRequestDispatcher("SearchProductController").forward(request, response);
                }
            }

        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AuthorFillter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthorFillter()");
        }
        StringBuffer sb = new StringBuffer("AuthorFillter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
