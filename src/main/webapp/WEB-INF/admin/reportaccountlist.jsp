<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="cust-tag" uri="/WEB-INF/taglib.tld" %>
<%@ page isELIgnored="false" %>


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<cust-tag:cacheOff/>

<!doctype html>
<html lang="${sessionScope.lang}">

<head>
    <style>
        <%@include file="../css/admin.css"%>
        <%@include file="../css/messageform.css"%>
        <%@include file="../css/list.css"%>
        <%@include file="../css/pagination.css"%>
        <%@include file="../css/table.css"%>
    </style>
</head>

<body>
<div class="header-panel">
    <label><h1>Hello ADMIN!</h1></label>
    <label><a href="${pageContext.request.contextPath}/logout">Logout</a></label>
    <label><a
            href="${pageContext.request.contextPath}/reportActivityList?sessionLocale=en&page=reportAccountList&pagenumber=${sessionScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <img src="static/flags/gb.svg" width="16" height="16"/>ENGLISH</a></label>
    <label><a
            href="${pageContext.request.contextPath}/reportActivityList?sessionLocale=ru&page=reportAccountList&pagenumber=${sessionScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <img src="static/flags/ru.svg" width="16" height="16"/>РУССКИЙ</a></label>
</div>
<br>
<hr>
<div class="vertical-menu">
    <a href="${pageContext.request.contextPath}/accountList?page=reportAccountList"><fmt:message
            key="a.admin.get_accounts"/></a>
    <a href="${pageContext.request.contextPath}/activityList?page=reportAccountList"><fmt:message
            key="a.admin.get_activities"/></a>
    <a href="${pageContext.request.contextPath}/kindList?page=reportAccountList"><fmt:message
            key="a.admin.get_kinds"/></a>
    <a href="${pageContext.request.contextPath}/requestList?page=reportAccountList"><fmt:message
            key="a.admin.get_requests"/></a>
    <a href="${pageContext.request.contextPath}/reportActivityList?page=reportAccountList"><fmt:message
            key="a.admin.get_report_activity"/></a>
    <a href="${pageContext.request.contextPath}/reportAccountList?page=reportAccountList"><fmt:message
            key="a.admin.get_report_account"/></a>
</div>

<div class="container">
    <div class="tab tab-1">
        <form action="http://localhost:8080/Web/reportAccountList" method="POST">
            <table class="table" id="table">
                <tr>
                    <th onclick="sortTableNum()"><fmt:message key="table.account.column.id"/></th>
                    <th onclick="sortTable(1)"><fmt:message key="table.account.column.first_name"/></th>
                    <th onclick="sortTable(2)"><fmt:message key="table.account.column.last_name"/></th>
                    <th onclick="sortTable(3)"><fmt:message key="report.account.total.activity"/></th>
                    <th onclick="sortTable(4)"><fmt:message key="report.account.total.time"/></th>
                </tr>
                <tr>
                    <td colspan="5">
                        <input style="width: 100%" type="text"
                               placeholder="<fmt:message key="message.activity.report.search"/>"
                               id="search-text" onkeyup="tableSearch()" class="input">
                    </td>
                </tr>
                <c:forEach var="list" items="${requestScope.accountReportList}">
                    <tr class="tblrow">
                        <td><c:out value="${list.id + 1}"/></td>
                        <td><c:out value="${list.firstName}"/></td>
                        <td><c:out value="${list.lastName}"/></td>
                        <td><c:out value="${list.activityCount}"/></td>
                        <td><c:out value="${list.totalTimeInMinutes}"/></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="5"><custom:pagination/></td>
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