<%-- 
    Document   : history
    Created on : Feb 27, 2020, 5:48:16 PM
    Author     : ngochuu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="https://kit.fontawesome.com/5a401084f7.js" crossorigin="anonymous"></script>
        <title>History Page</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div class="container" id="content">
            <h2 class="text-center">Transaction history</h2>
            <div class="row">
                <c:if test="${not empty requestScope.LIST_ORDER}" var="NoOrder">
                    <c:forEach items="${requestScope.LIST_ORDER}" var="order">
                        <table class="table table-striped col-lg-5 mr-auto ml-auto mb-5 mt-5 bg-light border border-warning">
                            <thead>
                                <tr>
                                    <th style="width: 25%; text-align: center;">Name</th>
                                    <th style="width: 25%; text-align: center;">IMG</th>
                                    <th style="width: 15%; text-align: center;">Price</th>
                                    <th style="width: 15%; text-align: center;">Quantity</th>
                                    <th style="width: 20%; text-align: center;">Amount</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="total" value="0"/>
                                <c:set var="LIST_ORDER_DETAILS" value="LIST_ORDER_DETAILS_${order.orderID}"/>
                                <c:set var="LIST_PRODUCT" value="LIST_PRODUCT_${order.orderID}"/>
                                <c:forEach items="${requestScope[LIST_ORDER_DETAILS]}" var="orderDetailsDTO">
                                    <c:set var="total" value="${total + (orderDetailsDTO.price * orderDetailsDTO.quantity)}"/>
                                    <tr>
                                        <td style="text-align: center;">${requestScope[LIST_PRODUCT][orderDetailsDTO.productID].productName}</td>
                                        <td style="text-align: center;">${requestScope[LIST_PRODUCT][orderDetailsDTO.productID].imgURL}</td>
                                        <td style="text-align: center;">${orderDetailsDTO.price}</td>
                                        <td style="text-align: center;">${orderDetailsDTO.quantity}</td>
                                        <td style="text-align: center;">${orderDetailsDTO.price * orderDetailsDTO.quantity}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td colspan="3"></td>
                                    <td class="text-center" colspan="1"><strong>Total</strong></td>
                                    <td class="text-center" colspan="1"><strong>$${total}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="5">
                                        <h4>Payment infomation:</h4>
                                        <p>Recipient's Name: ${order.recipientName}</p>
                                        <p>Recipient's Phone: ${order.orderPhone}</p>
                                        <p>Delivery address: ${order.orderAddress}</p>
                                        <p>Payment type: 
                                            <c:if test="${order.paymentType eq 'cast'}">Cash payment upon delivery</c:if>
                                            <c:if test="${order.paymentType eq 'paypal'}">Paypal payment</c:if>
                                            </p>
                                            <p>Payment date: ${order.checkoutDate.hours}:${order.checkoutDate.minutes} ${order.checkoutDate.date}/${order.checkoutDate.month + 1}/${order.checkoutDate.year + 1900}</p>
                                    </td>
                                </tr>
                            </tfoot>
                        </table>


                    </c:forEach>
                </c:if>
            </div>
            <c:if test="${!NoOrder}">
                <h4>There is no transaction history!!</h4>
            </c:if> 
        </div>
        <%@include file="footer.jsp" %>
    </body>
</html>
