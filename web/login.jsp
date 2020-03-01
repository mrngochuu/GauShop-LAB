<%-- 
    Document   : login
    Created on : Feb 24, 2020, 12:59:23 PM
    Author     : ngochuu
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <title>Login Page</title>
    </head>
    <body>
        <div class="container-fluid"> 
            <div class="row">
                <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                    <div class="card card-signin my-5">
                        <div class="card-body">
                            <h5 class="card-title text-center mb-3"><font color="red">${requestScope.MESSAGE}</font></h5>
                            <h3 class="card-title text-center">LOGIN FORM</h3>
                            <form action="MainController" method="POST" class="form-signin">
                                <input type="username" name="txtUsername" value="${param.txtUsername}" placeholder="Username" class="form-control mb-3" />
                                <font color="red"><p class="mb-3">${requestScope.INVALID.usernameError}</p></font>
                                <input type="password" name="txtPassword" placeholder="Password" class="form-control mb-3" />
                                <font class="mb-3" color="red"><p class="mb-3">${requestScope.INVALID.passwordError}</p></font>
                                <font class="mb-3" color="red"><p class="mb-3">${requestScope.INVALID.loginError}</p></font>
                                <input type="submit" name="action" value="Login" class="btn btn-lg btn-primary btn-block text-uppercase mb-3">
                            </form>
                            <hr class="my-4">
                            <div class="mb-3">
                                <c:url var="homeLink" value="MainController">
                                    <c:param name="action" value="SearchArticle"/>
                                </c:url>
                                <a href="${homeLink}">Home Page</a>
                            </div>
                            <div class="mb-3">
                                <a href="registration.jsp">Register Account</a>
                                <div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
