<%-- 
    Document   : index
    Created on : Feb 21, 2020, 7:23:51 PM
    Author     : ngochuu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="https://kit.fontawesome.com/5a401084f7.js" crossorigin="anonymous"></script>
        <title>Home Page</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div class="container-fluid"> 
            <div class="row">
                <div class="col-sm-12 col-md-12 col-lg-12 mx-auto">
                    <div class="card card-signin my-5">
                        <div class="card-body">
                            <h5 class="card-title text-center mb-3"><font color="red">${requestScope.MESSAGE}</font></h5>
                            <form action="MainController" method="GET" class="form-signin">
                                <input type="text" name="txtSearch" value="${param.txtSearch}" placeholder="Name" class="mb-3 mr-3"/>
                                <!-- Range -->
                                <select class="browser-default mb-2" name="cbRange">
                                    <option value="">Range of money</option>
                                    <option value="0-10" <c:if test="${param.cbRange eq '0-10'}">selected</c:if>>0$ - 10$</option>
                                    <option value="10-50" <c:if test="${param.cbRange eq '10-50'}">selected</c:if>>10$ - 50$</option>
                                    <option value="50-100" <c:if test="${param.cbRange eq '50-100'}">selected</c:if>>50$ - 100$</option>
                                    <option value="100-" <c:if test="${param.cbRange eq '100-'}">selected</c:if>> over 100$</option>
                                    </select>
                                    <!-- Category -->
                                    <select class="browser-default mb-2" name="cbCategory">
                                        <option value="0">Category</option>
                                    <c:forEach items="${sessionScope.LIST_CATEGORY}" var="categoryDTO">
                                        <option value="${categoryDTO.key}" <c:if test="${categoryDTO.key eq param.cbCategory}">selected</c:if>>${categoryDTO.value.categoryName}</option>
                                    </c:forEach>
                                </select>
                                <button type="submit" name="action" value="SearchProduct" class="mb-3">Search</button>
                            </form>

                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th style="width: 15%; text-align: center;">Image</th>
                                        <th style="width: 10%; text-align: center;">Product</th>
                                        <th style="width: 30%; text-align: center;">Description</th>
                                        <th style="width: 5%; text-align: center;">Price</th>
                                        <th style="width: 10%; text-align: center;">Create Date</th>
                                        <th style="width: 10%; text-align: center;">Category</th>
                                            <c:if test="${sessionScope.ROLE.roleName eq 'user'}">
                                            <th style="width: 10%; text-align: center;"></th>
                                            </c:if>
                                    </tr>
                                </thead>

                                <c:if test="${not empty requestScope.LIST_PRODUCT}">
                                    <c:forEach items="${LIST_PRODUCT}" var="dto">
                                        <tr>
                                            <td style="text-align: center;">
                                                <img src="http://localhost:8084/Image/${dto.imgURL}" width="80px" height="80px" >
                                            </td>
                                            <td style="text-align: center;">${dto.productName}</td>
                                            <td style="text-align: center;">${dto.description}</td>
                                            <td style="text-align: center;">$${dto.price}</td>
                                            <td style="text-align: center;">${dto.postingDate.hours}:${dto.postingDate.minutes} ${dto.postingDate.date}/${dto.postingDate.month + 1}/${dto.postingDate.year + 1900}</td>
                                            <td style="text-align: center;">${sessionScope.LIST_CATEGORY.get(dto.categoryID).categoryName}</td>
                                            <c:url var="addToCart" value="MainController">
                                                <c:param name="action" value="AddingProduct"/>
                                                <c:param name="productID" value="${dto.productID}"/>
                                                <c:param name="price" value="${dto.price}"/>
                                                <c:param name="quantity" value="${dto.quantity}"/>
                                                <c:param name="pageStr" value="${param.pageStr}"/>
                                                <c:param name="txtSearch" value="${param.txtSearch}" />
                                                <c:param name="cbRange" value="${param.cbRange}"/>
                                                <c:param name="cbCategory" value="${param.cbCategory}"/>
                                            </c:url>
                                            <c:if test="${sessionScope.ROLE.roleName eq 'user'}">
                                                <td style="text-align: center;"><a class="btn btn-block btn-secondary" href="${addToCart}">Add</a></td>
                                            </c:if>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </table>
                            Page: 
                            <c:forEach var='i' begin="1" end="${requestScope.TOTAL_PAGE}">
                                <c:url var="pageNum" value="MainController">
                                    <c:param name="action" value="SearchProduct"/>
                                    <c:param name="pageStr" value="${i}"/>
                                    <c:param name="txtSearch" value="${param.txtSearch}" />
                                    <c:param name="cbRange" value="${param.cbRange}"/>
                                    <c:param name="cbCategory" value="${param.cbCategory}"/>
                                </c:url>
                                <div class="mx-auto d-inline">
                                    <a href="${pageNum}">${i}</a>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="footer.jsp" %>
    </body>
</html>
