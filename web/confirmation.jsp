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
                    <div class="mb-4"><h2>${param.message}</h2></div>
                    <div class="row">
                        <form action="MainController" method="POST" class="m-auto">
                            <input type="hidden" name="productID" value="${param.productID}"/>
                            <input type="hidden" name="status" value="${param.status}"/>
                            <input type="hidden" name="category" value="${param.category}"/>
                            <input type="hidden" name="txtSearch" value="${param.txtSearch}"/>
                            <input type="hidden" name="cbStatus" value="${param.cbStatus}"/>
                            <input type="hidden" name="cbCategory" value="${param.cbCategory}"/>
                            <input type="hidden" name="confirm" value="yes"/>
                            <button type="submit" name="action" value="${param.yesAction}" class="btn btn-secondary text-center pl-5 pr-5"><h5>Yes</h5></button>
                        </form>

                        <form action="MainController" method="POST" class="m-auto">
                            <input type="hidden" name="productID" value="${param.productID}"/>
                            <input type="hidden" name="txtSearch" value="${param.txtSearch}"/>
                            <input type="hidden" name="cbStatus" value="${param.cbStatus}"/>
                            <input type="hidden" name="cbCategory" value="${param.cbCategory}"/>
                            <button type="submit" name="action" value="${param.noAction}" class="btn btn-secondary text-center pl-5 pr-5"><h5>No</h5></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
