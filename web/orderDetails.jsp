<%-- 
    Document   : orderDetails
    Created on : Feb 27, 2020, 12:56:24 AM
    Author     : ngochuu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <title>Payment Page</title>
    </head>
    <body>
        <div class="container-fluid"> 
            <div class="row">
                <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                    <div class="card card-signin my-5">
                        <div class="card-body">
                            <h3 class="card-title text-center mb-5">PAYMENT FORM</h3>
                            <h6 class="mb-3">Hello ${sessionScope.USER.fullname}, please fill in the information below to complete payment.</h6>
                            <form action="MainController" method="POST" class="form-signin">
                                <input type="text" name="txtRecipientName" value="${param.txtRecipientName}" placeholder="Recipient's name" class="form-control mb-3" />
                                <font class="mb-3" color="red"><p class="mb-3">${requestScope.INVALID.recipientNameError}</p></font>

                                <input type="text" name="txtPhone" placeholder="Recipient's phone number" value="${param.phoneNumber}" class="form-control mb-3" />
                                <font class="mb-3" color="red"><p class="mb-3">${requestScope.INVALID.phoneError}</p></font>

                                <input type="text" name="txtAddress" value="${param.txtAddress}" placeholder="Delivery address" class="form-control mb-3" />
                                <font color="red"><p class="mb-3">${requestScope.INVALID.addressError}</p></font>

                                <h5 class="mt-3 mb-3">Payment type:</h5>
                                <input type="radio" name="paymentType" value="cast" class="mb-3" checked/>Cash payment upon delivery<br>
                                <input type="radio" name="paymentType" value="paypal" class="mb-3"/>Paypal payment
                                <button type="submit" name="action" value="Pay" class="btn btn-lg btn-primary btn-block text-uppercase mb-3">Complete the payment</button>
                            </form>
                            <hr class="my-4">
                            <c:url var="homeLink" value="MainController">
                                <c:param name="action" value="SearchProduct"/>
                            </c:url>
                            <c:url var="showCartLink" value="MainController">
                                <c:param name="action" value="ShowCart"/>
                            </c:url>
                            <div class="mb-3">

                                <a href="${homeLink}">Home Page</a>
                            </div>

                            <div class="mb-3">
                                <a href="${showCartLink}">Cart Page</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
