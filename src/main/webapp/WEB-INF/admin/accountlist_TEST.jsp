<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="row" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">

<head>
    <title>ADMIN THE BASIS</title>
    <style>
        <%@include file="css/admin.css"%>
        <%@include file="rowEditForm.css"%>
    </style>
</head>

<body>
<div class="header-panel">
    <label><h1>Hello ADMIN!</h1></label>
    <label><a href="${pageContext.request.contextPath}/logout">Logout</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=en&page=accountList&pagenumber=${requestScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <span class="flag-icon flag-icon-gb"></span>ENGLISH</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=ru&page=accountList&pagenumber=${requestScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <span class="flag-icon flag-icon-ru"></span>РУСКИЙ</a></label>

</div>
<br>
<hr>
<div class="vertical-menu">
    <a href="${pageContext.request.contextPath}/accountList"><fmt:message key="a.admin.get_accounts"/></a>
    <a href="${pageContext.request.contextPath}/activityList"><fmt:message key="a.admin.get_activities"/></a>
    <a href="${pageContext.request.contextPath}/kindList"><fmt:message key="a.admin.get_kinds"/></a>
    <a href="${pageContext.request.contextPath}/requestList"><fmt:message key="a.admin.get_requests"/></a>
    <a href="#"><fmt:message key="a.admin.get_requests"/></a>
</div>

<%--<row:rowEditForm account="${requestScope.accounts.get(1)}" />--%>
<row:rowEditForm account="${requestScope.accounts.get(1)}"/>

<table id='table' border="1">
    <tr>
        <a href="${pageContext.request.contextPath}/accountCreate"><fmt:message key="entity.action.create"/></a>
    </tr>
    <tr>
        <th><fmt:message key="table.account.column.id"/></th>
        <th><fmt:message key="table.account.column.first_name"/></th>
        <th><fmt:message key="table.account.column.last_name"/></th>
        <th><fmt:message key="table.account.column.middle_name"/></th>
        <th><fmt:message key="table.account.column.email"/></th>
        <th><fmt:message key="table.account.column.md5"/></th>
        <th><fmt:message key="entity.action"/></th>
        <th>
    </tr>
    <c:forEach var="accounts" items="${requestScope.accounts}">
        <tr class="tblrow">
            <th><c:out value="${accounts.id}"/></th>
            <th><c:out value="${accounts.firstName}"/></th>
            <th><c:out value="${accounts.lastName}"/></th>
            <th><c:out value="${accounts.middleName}"/></th>
            <th><c:out value="${accounts.email}"/></th>
            <th><c:out value="${accounts.md5}"/></th>
            <th><fmt:message key="entity.action.delete"/></th>
                <%--            <th>--%>
                <%--                <button onclick="show('block')" class="regButton"><fmt:message key="entity.action.update"/> / <fmt:message key="entity.action.delete"/></button>--%>
                <%--&lt;%&ndash;                <a style="a:hover background-color: #fff;" href="${pageContext.request.contextPath}/accountUpdate?id=${accounts.id}"><fmt:message key="entity.action.update"/></a>&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/accountDelete?id=${accounts.id}"><fmt:message key="entity.action.delete"/></a>&ndash;%&gt;--%>
                <%--            </th>--%>
        </tr>
    </c:forEach>
</table>
<script>
    var index, table = document.getElementById('table');
    for (var i = 1; i < table.rows.length; i++) {
        table.rows[i].cells[6].onclick = function () {
            var c = confirm("do you want to delete this row");
            if (c === true) {
                index = this.parentElement.rowIndex;
                table.deleteRow(index);
            }
            console.log(index);
        };
    }
</script>

<hr/>
<div style="text-align: center; width:100%; background-color: #adb5bd">
    <c:if test="${sessionScope.pages.size() > 1}">
        <c:forEach items="${sessionScope.pages}" var="item" varStatus="status">
            <a href="${pageContext.request.contextPath}${item}">${status.count}</a>
        </c:forEach>
    </c:if>
</div>
<hr/>
</body>
</html>
