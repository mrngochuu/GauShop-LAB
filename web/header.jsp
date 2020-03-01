<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Header -->
<div class="container-fluid">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <c:url var="homeLink" value="MainController">
            <c:param name="action" value="SearchProduct"/>
        </c:url>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${homeLink}">Home</a>
                </li>
            </ul>
        </div>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto">
                <c:url var="logoutLink" value="MainController">
                    <c:param name="action" value="Logout"/>
                </c:url>
                <c:url var="showCartLink" value="MainController">
                    <c:param name="action" value="ShowCart"/>
                </c:url>
                <c:url var="searchHistoryLink" value="MainController">
                    <c:param name="action" value="SearchHistory"/>
                </c:url>
                <c:url var="managePostLink" value="MainController">
                    <c:param name="action" value="AdminSearchProduct"/>
                </c:url>
                <c:if test="${sessionScope.ROLE.roleName eq 'user'}" var="isUser">
                    <li class="nav-item">
                        <a class="nav-link mt-2" href="${searchHistoryLink}"><i class="fas fa-history mr-1"></i>History</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link mt-2" href="${showCartLink}"><i class="fas fa-shopping-cart mr-1"></i>Cart</a>
                    </li>
                    <li class="nav-item">
                        <p class="nav-link mt-2"><i class="fas fa-users-cog mr-1"></i>${sessionScope.USER.username}</p>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link mt-2" href="${logoutLink}"><i class="fas fa-sign-out-alt mr-1"></i>Logout</a>
                    </li>
                </c:if>

                <c:if test="${sessionScope.ROLE.roleName eq 'admin'}" var="isAdmin">
                    <li class="nav-item">
                        <a class="nav-link mt-2" href="creatingProduct.jsp" >
                            <i class="fas fa-plus mr-1"></i>Create product
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link mt-2" href="${managePostLink}" >
                            <i class="fas fa-tasks mr-1"></i>Manage product
                        </a>
                    </li>
                    <li class="nav-item">
                        <p class="nav-link mt-2"><i class="fas fa-users-cog mr-1"></i>${sessionScope.USER.username}</p>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link mt-2" href="${logoutLink}"><i class="fas fa-sign-out-alt mr-1"></i>Logout</a>
                    </li>
                </c:if>

                <c:if test="${!isUser && !isAdmin}">
                    <li class="nav-item">
                        <a class="nav-link mt-2" href="login.jsp"><i class="fas fa-sign-in-alt mr-1"></i>Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link mt-2" href="registration.jsp"><i class="fas fa-plus-circle mr-1"></i>Register</a>
                    </li>       
                </c:if>
            </ul>
        </div>
    </nav>
</div>
<!-- end header -->