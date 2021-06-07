<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title}</title>
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.css" rel="stylesheet">
    <!-- Bootstrap Bundle JS (jsDelivr CDN) -->
    <link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css" rel="stylesheet">
</head>

<body class="w-100 text-center" style="background-color: #e3e3e3;">
<main>
    <div class="d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <label class=""><a href="login.jsp?sessionLocale=en" class="link-secondary">
            <img src="${pageContext.request.contextPath}/static/flags/gb.svg" width="16"
                 height="16"/>ENGLISH</a></label>
        <label class=""><a href="login.jsp?sessionLocale=ru" class="link-secondary">
            <img src="${pageContext.request.contextPath}/static/flags/ru.svg" width="16"
                 height="16"/>РУССКИЙ</a></label>
        <%--        <label><a href="${pageContext.request.contextPath}/logout">Logout</a></label>--%>
        <form action="${pageContext.request.contextPath}/login" method="POST" role="form">
            <h1 class="h3 mb-3 fw-normal"><fmt:message key="label.welcome"/></h1>
            <div class="form-floating">
                <input name="email" type="email" class="form-control" id="floatingInput" placeholder="name@example.com">
                <label for="floatingInput"><fmt:message key="label.login"/></label>
            </div>
            <div class="form-floating">
                <input name="password" type="password" class="form-control" id="floatingPassword"
                       placeholder="Password">
                <label for="floatingPassword"><fmt:message key="label.password"/></label>
            </div>
            <button class="w-100 btn btn-lg btn-primary" type="submit" role="button">Sign in</button>
        </form>
    </div>
</main>
<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.js"></script>
</body>
</html>