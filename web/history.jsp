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
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">

        <script>
            $(function () {
                $("#datepicker").datepicker();
            });
        </script>
        <title>History Page</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div class="container-fluid"> 
            <div class="row">
                <div class="col-sm-12 col-md-12 col-lg-12 mx-auto">
                    <div class="card card-signin my-5">
                        <div class="card-body">
                            <h2 class="text-center">Transaction history</h2>
                            <form action="MainController" method="GET" class="mb-3"> 
                                <input type="text" name="txtSearch" value="${param.txtSearch}" placeholder="Product name"/>
                                <input type="text" name="dateStr" value="${param.dateStr}" id="datepicker" placeholder="dd/mm/yyyy" readonly="true"/>
                                <button name="action" value="SearchHistory">SEARCH</button>
                            </form>
                            <c:if test="${not empty requestScope.LIST_ORDER}" var="NoOrder">
                                <table class="table table-striped">
                                    <thead class="border border-warning" style="border: 1px;">
                                        <tr>
                                            <th style="width: 10%; text-align: center;">Product Name</th>
                                            <th style="width: 15%; text-align: center;">Product IMG</th>
                                            <th style="width: 5%; text-align: center;">Price</th>
                                            <th style="width: 5%; text-align: center;">Quantity</th>
                                            <th style="width: 5%; text-align: center;">Amount</th>
                                            
                                            <th style="width: 10%; text-align: center;">Recipient Name</th>
                                            <th style="width: 10%; text-align: center;">Recipient Phone</th>
                                            <th style="width: 10%; text-align: center;">Delivery Address</th>
                                            <th style="width: 15%; text-align: center;">Payment type</th>
                                            <th style="width: 5%; text-align: center;">Checkout Day</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${requestScope.LIST_ORDER}" var="order">
                                            <c:forEach items="${requestScope.HASTABLE_ORDER_DETAILS[order.orderID]}" var="details">
                                                <c:if test="${not empty requestScope.HASTABLE_PRODUCT[details.productID]}">
                                                    <tr>
                                                        <td style="text-align: center;">${requestScope.HASTABLE_PRODUCT[details.productID].productName}</td>
                                                        <td style="text-align: center;">${requestScope.HASTABLE_PRODUCT[details.productID].imgURL}</td>
                                                        <td style="text-align: center;">$${details.price}</td>
                                                        <td style="text-align: center;">${details.quantity}</td>
                                                        <td style="text-align: center;">$${details.quantity * details.price}</td>
                                                        
                                                        <td style="text-align: center;">${order.recipientName}</td>
                                                        <td style="text-align: center;">${order.orderPhone}</td>
                                                        <td style="text-align: center;">${order.orderAddress}</td>
                                                        <td style="text-align: center;">
                                                            <c:if test="${order.paymentType eq 'cast'}">Cash payment upon delivery</c:if>
                                                            <c:if test="${order.paymentType eq 'paypal'}">Paypal online</c:if>
                                                        </td>
                                                        <td style="text-align: center;">${order.checkoutDate.hours}:${order.checkoutDate.minutes} ${order.checkoutDate.date}/${order.checkoutDate.month + 1}/${order.checkoutDate.year + 1900}</td>
                                                    </tr>
                                                </c:if>
                                            </c:forEach>
                                        </c:forEach>
                                    </tbody>
                                    <tfoot>

                                    </tfoot>
                                </table>

                            </c:if>
                            <c:if test="${!NoOrder}">
                                <h4>There is no transaction history!!</h4>
                            </c:if> 
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="footer.jsp" %>
</body>
</html>
