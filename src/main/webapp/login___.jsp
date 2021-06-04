<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title}</title>
</head>
<body>
<div>
    <label><a href="login___.jsp?sessionLocale=en" class="link-secondary"><span
            class="flag-icon flag-icon-gb"></span>ENGLISH</a></label>
    <label><a href="login___.jsp?sessionLocale=ru" class="link-secondary"><span
            class="flag-icon flag-icon-ru"></span>РУССКИЙ</a></label>
    <label><a href="${pageContext.request.contextPath}/logout">Logout</a></label>
    <h1><fmt:message key="label.welcome"/></h1>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <div class="form-floating">
            <input name="email" type="email" class="form-control" id="floatingInput"
                   placeholder="name@example.com">
            <label for="floatingInput"><fmt:message key="label.login"/></label>
        </div>
        <div class="form-floating">
            <input name="password" type="password" class="form-control" id="floatingPassword"
                   placeholder="Password">
            <label for="floatingPassword"><fmt:message key="label.password"/></label>
        </div>
        <button type="submit" role="button"><fmt:message key="button.sign_in"/></button>
    </form>
</div>
<br/>
<a href="${pageContext.request.contextPath}/logout">На главную</a>

</body>
</html>