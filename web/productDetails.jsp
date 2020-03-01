<%-- 
    Document   : productDetails
    Created on : Feb 28, 2020, 6:18:03 PM
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
        <title>Product Page</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div class="container-fluid"> 
            <div class="row">
                <div class="col-sm-12 col-md-8 col-lg-8 mx-auto">
                    <h1 class="text-center"><font color="red"><p class="mt-3">${requestScope.MESSAGE}</p></font></h1>

                    <div class="card card-signin my-5">
                        <div class="card-body">
                            <form class="row" action="MainController" method="POST">
                                <input type="hidden" name="productID" value="${requestScope.PRODUCT.productID}"/>
                                <input type="hidden" name="imgURL" value="${requestScope.PRODUCT.imgURL}"/>
                                <div class="col-lg-3">
                                    ${requestScope.PRODUCT.imgURL}
                                    <font color="red"><p class="mb-3">${requestScope.INVALID.imgURLError}</p></font>
                                </div>
                                <div class="offset-lg-2 col-lg-7 border bg-light">
                                    <h3 class="font-weight-bold mb-3">NAME</h3>
                                    <input type="text" name="productName" value="${requestScope.PRODUCT.productName}" class="mb-3 form-control"/>
                                    <font color="red"><p class="mb-3">${requestScope.INVALID.productNameError}</p></font>
                                    <hr>
                                    <h3 class="mb-3">DESCRIPTION</h3>
                                    <p class="mb-3"><textarea name="description" class="mb-3 form-control">${requestScope.PRODUCT.description}</textarea></p>
                                    <font color="red"><p class="mb-3">${requestScope.INVALID.descriptionError}</p></font>
                                    <hr>
                                    <h3 class="mb-3">DETAILS</h3>
                                    <table>
                                        <tr>
                                            <td><p class="text-center font-weight-bold">Product Price:</p></td>
                                            <td><input type="number" step="0.1" name="price" value="${requestScope.PRODUCT.price}" style="width: 80px;" class="ml-3 mb-3 text-center"/></td>
                                        </tr>
                                        <tr >
                                            <td><p class="text-center font-weight-bold">Product Quantity:</p></td>
                                            <td><input type="number" name="quantity" value="${requestScope.PRODUCT.quantity}" style="width: 80px;" class="ml-3 mb-3 text-center"/></td>
                                        </tr>
                                        <tr>
                                            <td><p class="text-center font-weight-bold">Product Status:</p></td>
                                            <td >
                                                <select class="mb-3 ml-3 text-center" name="status" style="font-size: 20px;">
                                                    <option value="active" <c:if test="${requestScope.PRODUCT.status eq 'active'}">selected</c:if>>Active</option>
                                                    <option value="inactive" <c:if test="${requestScope.PRODUCT.status eq 'inactive'}">selected</c:if>>In Active</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><p class="text-center font-weight-bold">Product Category:</p></td>
                                                <td>
                                                    <select class="mb-3 ml-3 text-center" name="category" style="font-size: 20px;">
                                                    <c:forEach items="${sessionScope.LIST_CATEGORY}" var="entry">
                                                        <option value="${entry.key}" <c:if test="${entry.key eq requestScope.PRODUCT.categoryID}">selected</c:if>>${entry.value.categoryName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><p class="text-center font-weight-bold">Create/Update time:</p></td>
                                            <td><p class="mb-3 ml-3 text-center">${requestScope.PRODUCT.postingDate.hours}:${requestScope.PRODUCT.postingDate.minutes} ${requestScope.PRODUCT.postingDate.date}/${requestScope.PRODUCT.postingDate.month + 1}/${requestScope.PRODUCT.postingDate.year + 1900}</p></td>
                                        </tr>
                                    </table>
                                    <font color="red"><p class="mb-3">${requestScope.INVALID.priceError}</p></font>
                                    <font color="red"><p class="mb-3">${requestScope.INVALID.quantityError}</p></font>
                                    <font color="red"><p class="mb-3">${requestScope.INVALID.categoryError}</p></font>
                                    <font color="red"><p class="mb-3">${requestScope.INVALID.statusError}</p></font>
                                    <button class="form-control btn btn-primary btn-lg mb-3" name="action" value="AdminUpdateProductDetails">Update</button>
                            </form>
                            <c:if test="${requestScope.PRODUCT.status eq 'active'}">
                                <form action="confirmation.jsp">
                                    <input type="hidden" name="productID" value="${requestScope.PRODUCT.productID}" />
                                    <input type="hidden" name="status" value="inactive" />
                                    <input type="hidden" name="category" value="${requestScope.PRODUCT.categoryID}" />
                                    <input type="hidden" name="message" value="Do you want to delete ${requestScope.PRODUCT.productName}?"/>
                                    <input type="hidden" name="yesAction" value="AdminUpdateProduct" />
                                    <input type="hidden" name="noAction" value="AdminShowProduct" />
                                    <button class="form-control btn btn-danger btn-lg mb-3">Delete</button>
                                </form>
                            </c:if>
                            <c:if test="${requestScope.PRODUCT.status eq 'inactive'}">
                                <form action="confirmation.jsp">
                                    <input type="hidden" name="productID" value="${requestScope.PRODUCT.productID}" />
                                    <input type="hidden" name="status" value="active" />
                                    <input type="hidden" name="category" value="${requestScope.PRODUCT.categoryID}" />
                                    <input type="hidden" name="message" value="Do you want to restore ${requestScope.PRODUCT.productName}?"/>
                                    <input type="hidden" name="yesAction" value="AdminUpdateProduct" />
                                    <input type="hidden" name="noAction" value="AdminShowProduct" />
                                    <button class="form-control btn btn-danger btn-lg mb-3">Restore</button>
                                </form>
                            </c:if>
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
