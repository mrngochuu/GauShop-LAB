<%-- 
    Document   : confirmation
    Created on : Feb 27, 2020, 12:11:13 AM
    Author     : ngochuu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <title>Cart Page</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="ml-auto mr-auto p-3 bg-light">
                    <c:url var="showCartLink" value="MainController">
                        <c:param name="action" value="ShowCart"/>
                    </c:url>
                    <div class="mb-4"><h2>${param.mess}</h2></div>
                    <div class="row">
                        <form action="MainController" method="POST" class="m-auto">
                            <input type="hidden" name="productID" value="${param.productID}"/>
                            <button type="submit" name="action" value="DeleteFromCart" class="btn btn-secondary text-center pl-5 pr-5"><h5>Yes</h5></button>
                        </form>
                        <a href="${showCartLink}" class="btn btn-secondary text-center m-auto pl-5 pr-5"><h5>No</h5></a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
