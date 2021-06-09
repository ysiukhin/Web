<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import="ua.traning.rd.java.finalproject.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tag" uri="/WEB-INF/taglib.tld" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!doctype html>
<html lang="${sessionScope.lang}">
<head>
    <title>USER section</title>
    <style>
        <%@include file="../css/admin.css"%>
        <%@include file="../css/messageform.css"%>
        <%@include file="../css/list.css"%>
        <%@include file="../css/pagination.css"%>
        <%@include file="../css/table.css"%>
    </style>
    <tag:cacheOff/>
</head>
<body>
<c:if test="${sessionScope.isMessage}">
    <c:set scope="session" var="isMessage" value="false"/>
    <custom:messageform actionStatus="${sessionScope.actionStatus}"
                        actionCaption="${sessionScope.actionCaption}"
                        actionMessage="${sessionScope.actionMessage}"/>
</c:if>
<div class="header-panel">
    <label><h1>${sessionScope.account.account.firstName}&nbsp;${sessionScope.account.account.lastName}</h1></label>
    <label><h2>${sessionScope.account.account.email}</h2></label>
    <label><a href="${pageContext.request.contextPath}${Constants.COMMAND_LOGOUT}">Logout</a></label>
    <label><a
            href="${pageContext.request.contextPath}${Constants.COMMAND_CHANGE_LANGUAGE}?${Constants.SESSION_LOCALE}=${Constants.LOCALE_ENGLISH}&${Constants.PAGE}=${Constants.COMMAND_USER_SECTION}"
            class="link-secondary">
        <img src="${pageContext.request.contextPath}/static/flags/gb.png" width="20" height="16"/>ENGLISH</a></label>
    <label><a
            href="${pageContext.request.contextPath}${Constants.COMMAND_CHANGE_LANGUAGE}?${Constants.SESSION_LOCALE}=${Constants.LOCALE_RUSSIAN}&${Constants.PAGE}=${Constants.COMMAND_USER_SECTION}"
            class="link-secondary">
        <img src="${pageContext.request.contextPath}/static/flags/ru.png" width="20" height="16"/>РУССКИЙ</a></label>
</div>
<br>
<hr>
<div class="vertical-menu">
    <a href=<c:out value="${pageContext.request.contextPath}${Constants.COMMAND_USER_TIMER}"/>>
        <fmt:message key="a.user.get_set_activity_timer"/></a>
    <a href= <c:out value="${pageContext.request.contextPath}${Constants.COMMAND_USER_REQUEST_LIST}"/>>
        <fmt:message key="a.user.request.action"/></a>
</div>
<hr>
</body>
</html>