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
<%--<c:if test="${sessionScope.isMessage}">--%>
<%--    <custom:messageform actionStatus="${sessionScope.actionStatus}" actionMessage=""/>--%>
<%--    <c:set scope="session" var="isMessage" value="false"/>--%>
<%--</c:if>--%>

<div class="header-panel">
    <label><h1>Hello ADMIN!</h1></label>
    <label><a href="${pageContext.request.contextPath}/logout">Logout</a></label>
    <label><a
            href="${pageContext.request.contextPath}/reportActivityList?sessionLocale=en&page=reportActivityList&pagenumber=${sessionScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <span class="flag-icon flag-icon-gb"></span>ENGLISH</a></label>
    <label><a
            href="${pageContext.request.contextPath}/reportActivityList?sessionLocale=ru&page=reportActivityList&pagenumber=${sessionScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <span class="flag-icon flag-icon-ru"></span>РУСКИЙ</a></label>
</div>
<br>
<hr>
<div class="vertical-menu">
    <a href="${pageContext.request.contextPath}/accountList?page=reportActivityList"><fmt:message
            key="a.admin.get_accounts"/></a>
    <a href="${pageContext.request.contextPath}/activityList?page=reportActivityList"><fmt:message
            key="a.admin.get_activities"/></a>
    <a href="${pageContext.request.contextPath}/kindList?page=reportActivityList"><fmt:message
            key="a.admin.get_kinds"/></a>
    <a href="${pageContext.request.contextPath}/requestList?page=reportActivityList"><fmt:message
            key="a.admin.get_requests"/></a>
    <a href="${pageContext.request.contextPath}/reportActivityList?page=reportActivityList"><fmt:message
            key="a.admin.get_report_activity"/></a>
    <a href="${pageContext.request.contextPath}/reportAccountList?page=reportActivityList"><fmt:message
            key="a.admin.get_report_account"/></a>
</div>

<div class="container">
    <div class="tab tab-1">
        <form action="http://localhost:8080/Web/activityAction" method="POST">
            <table class="table" id="table">
                <tr>
                    <th onclick="sortTableNum()"><fmt:message key="table.activity.column.id"/></th>
                    <th onclick="sortTable(1)"><fmt:message key="table.activity.column.activity_kind"/></th>
                    <th onclick="sortTable(2)"><fmt:message key="table.request.column.activity"/></th>
                    <th onclick="sortTable(3)"><fmt:message key="report.activity.total.account"/></th>
                    <th onclick="sortTable(4)"><fmt:message key="report.activity.total.requests"/></th>
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
                        <td><c:out value="${list.id + 1}"/></td>
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
                        <td><c:out value="${list.requestCount}"/></td>
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