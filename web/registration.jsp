<%-- 
    Document   : register
    Created on : Feb 24, 2020, 12:59:36 PM
    Author     : ngochuu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <title>Register Page</title>
    </head>
    <body>
        <div class="container-fluid"> 
            <div class="row">
                <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                    <div class="card card-signin my-5">
                        <div class="card-body">
                            <h3 class="card-title text-center">REGISTRATION FORM</h3>
                            <form action="MainController" method="POST" class="form-signin">
                                <input type="text" name="txtUsername" value="${param.txtUsername}" placeholder="Username" class="form-control mb-3" />
                                <font color="red"><p class="mb-3">${requestScope.INVALID.usernameError}</p></font>
                                <font color="red"><p class="mb-3">${requestScope.INVALID.duplicationError}</p></font>
                                
                                <input type="text" name="txtFullname" value="${param.txtFullname}" placeholder="Full name" class="form-control mb-3"/>
                                <font color="red"><p class="mb-3">${requestScope.INVALID.fullnameError}</p></font>
                                
                                <input type="password" name="txtPassword" placeholder="Password" class="form-control mb-3" />
                                <font color="red"><p class="mb-3">${requestScope.INVALID.passwordError}</p></font>
                                
                                <input type="password" name="txtConfirm" placeholder="Confirm password" class="form-control mb-3" />
                                <font color="red"><p class="mb-3">${requestScope.INVALID.confirmError}</p></font>
                                
                                <input type="text" name="txtPhone" value="${param.txtAddress}" placeholder="Phone" class="form-control mb-3"/>
                                <font color="red"><p class="mb-3">${requestScope.INVALID.phoneError}</p></font>
                                
                                <input type="text" name="txtAddress" value="${param.txtAddress}" placeholder="Address" class="form-control mb-3"/>
                                <font color="red"><p class="mb-3">${requestScope.INVALID.addressError}</p></font>
                                
                                <input type="submit" name="action" value="Register" class="btn btn-lg btn-primary btn-block text-uppercase mb-3">
                            </form>
                            <hr class="mb-3">
                            <div class="mb-3">
                                <c:url var="homeLink" value="MainController">
                                    <c:param name="action" value="SearchArticle"/>
                                </c:url>
                                <a href="${homeLink}">Home Page</a>
                            </div>
                            <div class="mb-3">
                                <a href="login.jsp">Login</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

