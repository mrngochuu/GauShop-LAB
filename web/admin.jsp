<%-- 
    Document   : admin
    Created on : Feb 27, 2020, 11:31:12 PM
    Author     : ngochuu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="https://kit.fontawesome.com/5a401084f7.js" crossorigin="anonymous"></script>
        <title>Admin Page</title>
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
                                <!-- Category -->
                                <select class="browser-default mb-2" name="cbCategory">
                                    <option value="0">Category</option>
                                    <c:forEach items="${sessionScope.LIST_CATEGORY}" var="categoryDTO">
                                        <option value="${categoryDTO.key}" <c:if test="${categoryDTO.key eq param.cbCategory}">selected</c:if>>${categoryDTO.value.categoryName}</option>
                                    </c:forEach>
                                </select>
                                <!-- Status -->
                                <select class="browser-default mb-2" name="cbStatus">
                                    <option value="">Status</option>
                                    <option value="active" <c:if test="${param.cbStatus eq 'active'}">selected</c:if>>Active</option>
                                    <option value="inactive" <c:if test="${param.cbStatus eq 'inactive'}">selected</c:if>>In Active</option>
                                    </select>
                                    <button type="submit" name="action" value="AdminSearchProduct" class="mb-3">Search</button>
                                </form>

                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th style="width: 20%; text-align: center;">Image</th>
                                            <th style="width: 15%; text-align: center;">Product</th>
                                            <th style="width: 20%; text-align: center;">Description</th>
                                            <th style="width: 5%; text-align: center;">Price</th>
                                            <th style="width: 5%; text-align: center;">Quantity</th>
                                            <th style="width: 5%; text-align: center;">Category</th>
                                            <th style="width: 5%; text-align: center;">Status</th>
                                            <th style="width: 5%; text-align: center;">Create/Update time</th>
                                            <th style="width: 10%; text-align: center;">Show Details</th>
                                            <th style="width: 10%; text-align: center;">Delete</th>
                                        </tr>
                                    </thead>

                                <c:if test="${not empty requestScope.LIST_PRODUCT}">
                                    <c:forEach items="${LIST_PRODUCT}" var="dto">
                                        <tr>
                                        <form action="MainController" method="POST">
                                            <td style="text-align: center;">
                                                <img src="http://localhost:8084/Image/${dto.imgURL}" width="80px" height="80px" >
                                            </td>
                                            <td style="text-align: center;">${dto.productName}</td>
                                            <td style="text-align: center;">${dto.description}</td>
                                            <td style="text-align: center;">$${dto.price}</td>
                                            <td style="text-align: center;">${dto.quantity}</td>
                                            <td style="text-align: center;">
                                                <select class="browser-default mb-2" name="category" onchange="this.form.submit()">
                                                    <c:forEach items="${sessionScope.LIST_CATEGORY}" var="entry">
                                                        <option value="${entry.key}" <c:if test="${entry.key eq dto.categoryID}">selected</c:if>>${entry.value.categoryName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <select class="browser-default mb-2" name="status" onchange="this.form.submit()">
                                                    <option value="active" <c:if test="${dto.status eq 'active'}">selected</c:if>>Active</option>
                                                    <option value="inactive" <c:if test="${dto.status eq 'inactive'}">selected</c:if>>In Active</option>
                                                    </select>
                                                </td>
                                                <td style="text-align: center;">${dto.postingDate.hours}:${dto.postingDate.minutes} ${dto.postingDate.date}/${dto.postingDate.month + 1}/${dto.postingDate.year + 1900}</td>
                                            <input type="hidden" name="productID" value="${dto.productID}"/>
                                            <input type="hidden" name="statusProduct" value="${dto.status}"/>
                                            <input type="hidden" name="txtSearch" value="${param.txtSearch}"/>
                                            <input type="hidden" name="cbStatus" value="${param.cbStatus}"/>
                                            <input type="hidden" name="cbCategory" value="${param.cbCategory}"/>
                                            <input type="hidden" name="message" value="Do you want to change ${dto.productName}'s status from ${dto.status} to <c:if test="${dto.status eq 'active'}">inactive</c:if><c:if test="${dto.status eq 'inactive'}">active</c:if>?"/>
                                                <input type="hidden" name="yesAction" value="AdminUpdateProduct"/>
                                                <input type="hidden" name="noAction" value="AdminSearchProduct"/>
                                                <input type="hidden" name="action" value="AdminUpdateProduct"/>
                                            </form>
                                            <td>
                                            <c:if test="${dto.status eq 'active'}">
                                                <form action="MainController" method="POST">
                                                    <input type="hidden" name="productID" value="${dto.productID}"/>
                                                    <input type="hidden" name="statusProduct" value="${dto.status}"/>
                                                    <input type="hidden" name="status" value="inactive"/>
                                                    <input type="hidden" name="category" value="${dto.categoryID}"/>
                                                    <input type="hidden" name="txtSearch" value="${param.txtSearch}"/>
                                                    <input type="hidden" name="cbStatus" value="${param.cbStatus}"/>
                                                    <input type="hidden" name="cbCategory" value="${param.cbCategory}"/>
                                                    <input type="hidden" name="message" value="Do you want to delete ${dto.productName}?"/>
                                                    <input type="hidden" name="yesAction" value="AdminUpdateProduct"/>
                                                    <input type="hidden" name="noAction" value="AdminSearchProduct"/>
                                                    <button type="submit" class="btn btn-danger" name="action" value="AdminUpdateProduct">Delete</button>
                                                </form>
                                            </c:if>
                                        </td>
                                        <td>
                                            <form action="MainController" method="POST">
                                                <input type="hidden" name="productID" value="${dto.productID}"/>
                                                <button type="submit" class="btn btn-secondary" name="action" value="AdminShowProduct">Details</button>
                                            </form>
                                        </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </table>
                            Page: 
                            <c:forEach var='i' begin="1" end="${requestScope.TOTAL_PAGE}">
                                <c:url var="pageNum" value="MainController">
                                    <c:param name="action" value="AdminSearchProduct"/>
                                    <c:param name="pageStr" value="${i}"/>
                                    <c:param name="txtSearch" value="${param.txtSearch}" />
                                    <c:param name="cbCategory" value="${param.cbCategory}"/>
                                    <c:param name="cbStatus" value="${param.cbStatus}"/>
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
