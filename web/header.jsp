<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Header -->
<div class="container-fluid">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <c:url var="homeLink" value="MainController">
            <c:param name="action" value="SearchArticle"/>
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
                <c:url var="manageArticlesLink" value="MainController">
                    <c:param name="action" value="AdminSearchArticle"/>
                </c:url>
                <c:if test="${sessionScope.ROLE.roleName eq 'member'}" var="isUser">
                    <li class="nav-item">
                        <a class="nav-link mt-2" href="postArticle.jsp">Post Article</a>
                    </li>
                    <li class="nav-item">
                        <p class="nav-link mt-2">${sessionScope.USER.name}</p>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link mt-2" href="${logoutLink}">Logout</a>
                    </li>
                </c:if>

                <c:if test="${sessionScope.ROLE.roleName eq 'admin'}" var="isAdmin">
                    <li class="nav-item">
                        <a class="nav-link mt-2" href="${manageArticlesLink}" >
                            Manage
                        </a>
                    </li>
                    <li class="nav-item">
                        <p class="nav-link mt-2">${sessionScope.USER.name}</p>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link mt-2" href="${logoutLink}">Logout</a>
                    </li>
                </c:if>

                <c:if test="${!isUser && !isAdmin}">
                    <li class="nav-item">
                        <a class="nav-link mt-2" href="login.jsp">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link mt-2" href="registration.jsp">Register</a>
                    </li>       
                </c:if>
            </ul>
        </div>
    </nav>
</div>
<!-- end header -->