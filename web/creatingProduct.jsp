<%-- 
    Document   : creatingProduct
    Created on : Feb 29, 2020, 4:40:27 PM
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
        <title>Creating Product Page</title>
        <style>
            input[type='file'] {
                color: transparent;
            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div class="container"> 
            <div class="row">
                <div class="col-sm-12 col-md-12 col-lg-12 mx-auto">
                    <div class="card card-signin my-5">
                        <div class="card-body row">
                            <div class="col-lg-5 mt-auto mb-auto pt-3 rounded border">
                                <img src="http://localhost:8084/Image/${requestScope.imgURL}" style="width: 100%; height: auto;">
                                <form action="UploadImageController" enctype="multipart/form-data" method="POST">
                                    <input type="file" name="file" onchange="this.form.submit()"/>
                                    <input type="hidden" name="productName" value="${param.productName}"/>
                                    <input type="hidden" name="description" value="${param.description}"/>
                                    <input type="hidden" name="price" value="${param.price}"/>
                                    <input type="hidden" name="quantity" value="${param.quantity}"/>
                                    <input type="hidden" name="category" value="${param.category}"/>
                                </form>
                                <font color="red"><p class="mb-3">${requestScope.INVALID.imgURLError}</p></font>
                            </div>
                            <div class="offset-lg-1 col-lg-6 border bg-light">
                                <form action="MainController" method="POST">
                                    <input type="hidden" name="imgURL" value="${requestScope.imgURL}"/>
                                    <h3 class="font-weight-bold mb-3">NAME</h3>
                                    <input type="text" name="productName" value="${param.productName}" class="mb-3 form-control"/>
                                    <font color="red"><p class="mb-3">${requestScope.INVALID.productNameError}</p></font>
                                    <hr>
                                    <h3 class="mb-3">DESCRIPTION</h3>
                                    <p class="mb-3"><textarea name="description" class="mb-3 form-control">${param.description}</textarea></p>
                                    <font color="red"><p class="mb-3">${requestScope.INVALID.descriptionError}</p></font>
                                    <hr>
                                    <h3 class="mb-3">DETAILS</h3>
                                    <table>
                                        <tr>
                                            <td><p class="font-weight-bold">Product Price:</p></td>
                                            <td>$<input type="number" step="0.1" name="price" value="${param.price}" style="width: 80px;" class="ml-3 mb-3 text-center"/></td>
                                        </tr>
                                        <tr >
                                            <td><p class="font-weight-bold">Product Quantity:</p></td>
                                            <td><input type="number" name="quantity" value="${param.quantity}" style="width: 80px;" class="ml-3 mb-3 text-center"/></td>
                                        </tr>
                                        <tr>
                                            <td><p class="font-weight-bold">Product Category:</p></td>
                                            <td>
                                                <select class="mb-3 ml-3 text-center" name="category" style="font-size: 20px;">
                                                    <c:forEach items="${sessionScope.LIST_CATEGORY}" var="entry">
                                                        <option value="${entry.key}" <c:if test="${entry.key eq param.category}">selected</c:if>>${entry.value.categoryName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                    </table>
                                    <font color="red"><p class="mb-3">${requestScope.INVALID.priceError}</p></font>
                                    <font color="red"><p class="mb-3">${requestScope.INVALID.quantityError}</p></font>
                                    <font color="red"><p class="mb-3">${requestScope.INVALID.categoryError}</p></font>
                                    <button class="form-control btn btn-primary btn-lg mb-3" name="action" value="AdminCreateProduct">CREATE</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="footer.jsp" %>
</body>
</html>
