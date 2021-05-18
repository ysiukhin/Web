<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%--<%@ page isELIgnored="false" %>--%>
<%--<%@ page session="true" %>--%>
<%--<fmt:setLocale value="${sessionScope.lang}"/>--%>
<%--<fmt:setBundle basename="messages"/>--%>

<html lang="${sessionScope.lang}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title}</title>
    <link href="${pageContext.request.contextPath}/static/bootstrap-5.0.1/css/bootstrap.css" rel="stylesheet">
</head>
<jsp:include page="${currentPage}"/>
</html>
<script src="${pageContext.request.contextPath}/static/bootstrap-5.0.1/js/bootstrap.js"></script>