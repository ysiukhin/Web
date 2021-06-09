<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import="ua.traning.rd.java.finalproject.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tag" uri="/WEB-INF/taglib.tld" %>
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
    <label><h1>Hello ADMIN!</h1></label>
    <label><a href="${pageContext.request.contextPath}${Constants.COMMAND_LOGOUT}">Logout</a></label>
    <label><a
            href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_REPORT_REQUEST_LIST}?sessionLocale=en&page=${Constants.COMMAND_ADMIN_REPORT_REQUEST_LIST}&${Constants.PAGE_NUMBER}=${sessionScope.pagenumber}&${Constants.ROWS_PER_PAGE}=${requestScope.rowsPerPage}">
        <img src="${pageContext.request.contextPath}/static/flags/gb.svg" width="16" height="16"/>ENGLISH</a></label>
    <label><a
            href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_REPORT_REQUEST_LIST}?sessionLocale=ru&page=${Constants.COMMAND_ADMIN_REPORT_REQUEST_LIST}&${Constants.PAGE_NUMBER}=${sessionScope.pagenumber}&${Constants.ROWS_PER_PAGE}=${requestScope.rowsPerPage}">
        <img src="${pageContext.request.contextPath}/static/flags/ru.svg" width="16" height="16"/>РУССКИЙ</a></label>
</div>
<br>
<hr>
<div class="vertical-menu">
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_ACCOUNT_LIST}?${Constants.PAGE}=${Constants.COMMAND_ADMIN_REPORT_REQUEST_LIST}"><fmt:message
            key="a.admin.get_accounts"/></a>
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_ACTIVITY_LIST}?${Constants.PAGE}=${Constants.COMMAND_ADMIN_REPORT_REQUEST_LIST}"><fmt:message
            key="a.admin.get_activities"/></a>
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_KIND_LIST}?${Constants.PAGE}=${Constants.COMMAND_ADMIN_REPORT_REQUEST_LIST}"><fmt:message
            key="a.admin.get_kinds"/></a>
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_REQUEST_LIST}?${Constants.PAGE}=${Constants.COMMAND_ADMIN_REPORT_REQUEST_LIST}"><fmt:message
            key="a.admin.get_requests"/></a>
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_REPORT_REQUEST_LIST}?${Constants.PAGE}=${Constants.COMMAND_ADMIN_REPORT_REQUEST_LIST}"><fmt:message
            key="a.admin.get_report_activity"/></a>
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_REPORT_ACCOUNT_LIST}?${Constants.PAGE}=${Constants.COMMAND_ADMIN_REPORT_REQUEST_LIST}"><fmt:message
            key="a.admin.get_report_account"/></a>
</div>

<div class="container">
    <div class="tab tab-1">
        <form action="#" method="POST">
            <table class="table" id="table">
                <tr>
                    <th onclick="sortTableNum()"><fmt:message key="table.activity.column.id"/></th>
                    <th onclick="sortTable(1)"><fmt:message key="table.activity.column.activity_kind"/></th>
                    <th onclick="sortTable(2)"><fmt:message key="table.request.column.activity"/></th>
                    <th onclick="sortTable(3)"><fmt:message key="report.activity.total.account"/></th>
<%--                    <th onclick="sortTable(4)"><fmt:message key="report.activity.total.requests"/></th>--%>
                </tr>
                <tr>
                    <td colspan="5">
                        <input style="width: 100%" type="text"
                               placeholder="<fmt:message key="message.activity.report.search"/>"
                               id="search-text" onkeyup="tableSearch()" class="input">
                    </td>
                </tr>
                <c:forEach var="list" items="${requestScope.activityReportList}">
                    <tr class="tblrow">
                        <td><c:out value="${list.id}"/></td>
                        <c:choose>
                            <c:when test="${sessionScope.lang eq 'en'}">
                                <td><c:out value="${list.kindEn}"/></td>
                                <td><c:out value="${list.activityRu}"/></td>
                            </c:when>
                            <c:otherwise>
                                <td><c:out value="${list.kindRu}"/></td>
                                <td><c:out value="${list.activityEn}"/></td>
                            </c:otherwise>
                        </c:choose>
                        <td><c:out value="${list.accountCount}"/></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="4"><custom:pagination/></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<hr/>
<script>
    <%@include file="../js/search.js"%>
</script>
</body>
</html>