<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import="ua.traning.rd.java.finalproject.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" uri="/WEB-INF/taglib.tld" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="${Constants.MESSAGES_BUNDLE}"/>

<!doctype html>
<html lang="${sessionScope.lang}">
<head>
    <title>ADMIN SECTION</title>
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
    <c:set scope="session" var="${Constants.IS_MESSAGE_TO_SHOW}" value="false"/>
    <custom:messageform actionStatus="${sessionScope.actionStatus}"
                        actionCaption="${sessionScope.actionCaption}"
                        actionMessage="${sessionScope.actionMessage}"/>
</c:if>
<div class="header-panel">
    <label><h1>Hello ADMIN</h1></label>
    <label><a href="${pageContext.request.contextPath}${Constants.COMMAND_LOGOUT}">Logout</a></label>
    <label><a
            href="${pageContext.request.contextPath}${Constants.COMMAND_CHANGE_LANGUAGE}?sessionLocale=en&${Constants.PAGE}=adminsection"
            class="link-secondary"><img src="${pageContext.request.contextPath}/static/flags/gb.svg" width="16"
                                        height="16"/>ENGLISH</a></label>
    <label><a
            href="${pageContext.request.contextPath}${Constants.COMMAND_CHANGE_LANGUAGE}?sessionLocale=ru&${Constants.PAGE}=adminsection"
            class="link-secondary"><img src="${pageContext.request.contextPath}/static/flags/ru.svg" width="16"
                                        height="16"/>РУССКИЙ</a></label>
</div>
<br>
<hr>
<div class="vertical-menu">
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_ACCOUNT_LIST}"><fmt:message
            key="a.admin.get_accounts"/></a>
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_ACTIVITY_LIST}"><fmt:message
            key="a.admin.get_activities"/></a>
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_KIND_LIST}"><fmt:message
            key="a.admin.get_kinds"/></a>
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_REQUEST_LIST}"><fmt:message
            key="a.admin.get_requests"/></a>
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_REPORT_REQUEST_LIST}"><fmt:message
            key="a.admin.get_report_activity"/></a>
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_REPORT_ACCOUNT_LIST}"><fmt:message
            key="a.admin.get_report_account"/></a>
</div>
<hr>
</body>
</html>