<%-- 
    Document   : cart
    Created on : Feb 26, 2020, 5:46:36 PM
    Author     : ngochuu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <title>Cart Page</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div class="container mt-lg-4" id="content">
            <h2>Your shopping cart</h2>
            <h4 class="text-center"><font color="red">${requestScope.MESSAGE}</font></h4>
                <c:if test="${not empty requestScope.LIST_ORDER_DETAILS}" var="NoOrder">

                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th style="width: 20%; text-align: center;">Name</th>
                            <th style="width: 25%; text-align: center;">IMG</th>
                            <th style="width: 25%; text-align: center;">Description</th>
                            <th style="width: 5%; text-align: center;">Price</th>
                            <th style="width: 5%; text-align: center;">Quantity</th>
                            <th style="width: 10%; text-align: center;">Amount</th>
                            <th style="width: 10%; text-align: center;">Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="total" value="0"/>
                        <c:forEach items="${requestScope.LIST_ORDER_DETAILS}" var="orderDTO">
                            <c:set var="total" value="${total + (orderDTO.price * orderDTO.quantity)}"/>
                            <tr>
                                <td style="text-align: center;">${requestScope.LIST_PRODUCT[orderDTO.productID].productName}</td>
                                <td style="text-align: center;">${requestScope.LIST_PRODUCT[orderDTO.productID].imgURL}</td>
                                <td style="text-align: center;">${requestScope.LIST_PRODUCT[orderDTO.productID].description}</td>
                                <td style="text-align: center;">${orderDTO.price}</td>
                                <td style="text-align: center;">
                                    <form action="MainController" method="POST">
                                        <input class="text-center" type="number" name="quantity" value="${orderDTO.quantity}" onchange="this.form.submit()"/>
                                        <input type="hidden" name="productID" value="${orderDTO.productID}"/>
                                        <input type="hidden" name="action" value="UpdateQuantity"/>
                                    </form>
                                </td>
                                <td style="text-align: center;">${orderDTO.quantity * orderDTO.price}</td>
                                <!-- delete -->
                                <td class="active">
                                    <form action="confirmation.jsp" method="POST">
                                        <input type="hidden" name="productID" value="${orderDTO.productID}"/>
                                        <input type="hidden" name="mess" value="Do you want to delete ${requestScope.LIST_PRODUCT[orderDTO.productID].productName} ?"/>
                                        <input type="submit" class="btn btn-block btn-warning" value="Delete">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="4"></td>
                            <td class="text-center" colspan="1"><strong>Total</strong></td>
                            <td class="text-center"><strong>$${total}</strong></td>
                        </tr>

                        <tr>
                            <td colspan="2">
                                <c:url value="MainController" var="homeLink">
                                    <c:param name="action" value="SearchProduct"/>
                                </c:url>
                                <a href="${homeLink}" class="btn btn-primary text-center">Home</a>
                            </td>
                            <td colspan="3"></td>
                            <td colspan="2"><a href="orderDetails" class="btn btn-danger text-justify">Click continue to pay</a></td>
                        </tr>
                    </tfoot>
                </table>
            </c:if>
            <c:if test="${!NoOrder}">
                <h4>There is no added product!</h4>
            </c:if> 
        </div>
        <%@include file="footer.jsp" %>
    </body>
</html>
