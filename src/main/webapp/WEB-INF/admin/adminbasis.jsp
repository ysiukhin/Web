<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" uri="/WEB-INF/taglib.tld" %>

<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!doctype html>
<html lang="${sessionScope.lang}">
<head>
    <title>ADMIN THE BASIS</title>
    <style>
        <%@include file="css/admin.css"%>
        <%@include file="css/messageform.css"%>
        <%@include file="css/account_list.css"%>
        <%@include file="css/pagination.css"%>
    </style>
    <custom:cacheOff/>
</head>
<body>
<%--<header class="page-header">--%>
<div class="header-panel">
    <label><h1>Hello ADMIN!</h1></label>
    <label><a href="${pageContext.request.contextPath}/logout">Logout</a></label>
    <label><a href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=en&page=adminsection"
              class="link-secondary">
        <span class="flag-icon flag-icon-gb"></span>ENGLISH</a></label>
    <label><a href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=ru&page=adminsection"
              class="link-secondary">
        <span class="flag-icon flag-icon-ru"></span>РУСКИЙ</a></label>
</div>
<%--</header>--%>
<%--<main>--%>
<br>
<hr>
<div class="vertical-menu">
    <a href="${pageContext.request.contextPath}/accountList"><fmt:message key="a.admin.get_accounts"/></a>
    <a href="${pageContext.request.contextPath}/activityList"><fmt:message key="a.admin.get_activities"/></a>
    <a href="${pageContext.request.contextPath}/kindList"><fmt:message key="a.admin.get_kinds"/></a>
    <a href="${pageContext.request.contextPath}/requestList"><fmt:message key="a.admin.get_requests"/></a>
    <a href="#"><fmt:message key="a.admin.get_requests"/></a>
</div>
<hr>
<%--</main>--%>
</body>
</html>