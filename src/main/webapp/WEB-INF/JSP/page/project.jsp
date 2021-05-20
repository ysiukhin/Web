<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<body class="w-100 text-center" style="background-color: #e3e3e3;">
<main>
    <div class="d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <form action="testdata" method="GET" role="form">
            <h1 class="h3 mb-3 fw-normal"><fmt:message key="label.project"/>&nbsp;<fmt:message key="label.page"/></h1>
            <p class="mt-3 mb-3 text-muted"><fmt:message key="label.author"/></p>
        </form>
    </div>
</main>
</body>
<%--</html>--%>