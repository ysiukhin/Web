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
    <link href="${pageContext.request.contextPath}/static/bootstrap-5.0.1/css/bootstrap.css" rel="stylesheet">
    <link href="static/bootstrap-5.0.1/css/bootstrap.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/2.3.1/css/flag-icon.min.css" rel="stylesheet"/>
</head>

<body class="w-100 text-center" style="background-color: #e3e3e3;">
<main>
    <div class="d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <label class=""><a href="index.jsp?sessionLocale=en" class="link-secondary"><span
                class="flag-icon flag-icon-gb"></span>ENGLISH</a></label>
        <label class=""><a href="index.jsp?sessionLocale=ru" class="link-secondary"><span
                class="flag-icon flag-icon-ru"></span>РУСКИЙ</a></label>
        <form action="testdata" method="POST" role="form">
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
            <div class="checkbox mb-3">
                <input name="remember_me" type="checkbox" value="remember-me"> Remember me
            </div>
            <button class="w-100 btn btn-lg btn-primary" type="submit" role="button">Sign in</button>
            <p class="mt-3 mb-3 text-muted">&copy; Siukhin Yuriy 2021</p>
        </form>
    </div>
</main>
</body>
</html>
<script src="/web/static/bootstrap-5.0.1/js/bootstrap.js"></script>