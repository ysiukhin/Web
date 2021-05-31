<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">

<head>
    <title>ADMIN</title>
    <style>
        <%@include file="css/admin.css" %>
    </style>
</head>

<body>
<div c lass="header-panel">
    <label><h1>Hello ADMIN!</h1></label>
    <label><a href="${pageContext.request.contextPath}/logout">Logout</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=en&page=kindList&pagenumber=${requestScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">

        <%--        <a href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=en&page=accountlist.jsp&pagenumber=1&rowsPerPage=10">--%>
        <%--    <label><a href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=en&page=accountlist.jsp&pagenumber=<c:out value="${requestScope.pagenumber}"/>&rowsPerPage=<c:out value="${requestScope.rowsPerPage}"/>">--%>
        <span class="flag-icon flag-icon-gb"></span>ENGLISH</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=ru&page=kindList&pagenumber=${requestScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <%--    <label><a href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=ru&page=accountlist.jsp&pagenumber=1&rowsPerPage=10">--%>
        <%--    <label><a href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=ru&page=accountlist.jsp&pagenumber=<c:out value="${requestScope.pagenumber}"/>&rowsPerPage=<c:out value="${requestScope.rowsPerPage}"/>"--%>
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
<table>
    <tr>
        <a href="${pageContext.request.contextPath}/kindCreate"><fmt:message key="entity.action.create"/></a>
    </tr>
    <tr>
        <th><fmt:message key="table.kind.column.id"/></th>
        <th><fmt:message key="table.kind.column.kind"/></th>
        <th><fmt:message key="entity.action"/></th>
    </tr>
    <c:forEach var="kind" items="${requestScope.kinds}">
        <tr>
            <th><c:out value="${kind.id}"/></th>
            <c:choose>
                <c:when test="${sessionScope.lang eq 'en'}">
                    <th><c:out value="${kind.kindEn}"/></th>
                </c:when>
                <c:otherwise>
                    <th><c:out value="${kind.kindRu}"/></th>
                </c:otherwise>
            </c:choose>
            <th>
                <a style="a:hover background-color: #fff;"
                   href="${pageContext.request.contextPath}/kindUpdate?id=${kind.id}"><fmt:message
                        key="entity.action.update"/></a>&nbsp;&nbsp;<a
                    href="${pageContext.request.contextPath}/kindDelete?id=${kind.id}"><fmt:message
                    key="entity.action.delete"/></a>
            </th>
        </tr>
    </c:forEach>
</table>
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
