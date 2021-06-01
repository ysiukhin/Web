<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
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
        <%@include file="/static/css/admin.css"%>
        <%@include file="/static/css/messageform.css"%>
        <%@include file="/static/css/account_list.css"%>
    </style>
</head>

<body>
<c:if test="${sessionScope.isMessage}">
    <custom:messageform actionStatus="${sessionScope.actionStatus}" actionMessage=""/>
    <c:set scope="session" var="isMessage" value="false"/>
</c:if>
<div class="header-panel">
    <label><h1>Hello ADMIN!</h1></label>
    <label><a href="${pageContext.request.contextPath}/logout">Logout</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=en&page=activityList&pagenumber=${requestScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <span class="flag-icon flag-icon-gb"></span>ENGLISH</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=ru&page=activityList&pagenumber=${requestScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
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

<div class="container">
    <div class="tab tab-1">
        <%--        <form action="http://localhost:8080/Web/activityAction" method="POST">--%>
        <form action="#" method="POST">
            <table border="1">
                <tr>
                    <td><input type="submit" value="<fmt:message key="entity.action.create"/>" name="action"
                               class="input"></td>
                    <td><input type="submit" value="<fmt:message key="entity.action.update"/>" name="action"
                               class="input"></td>
                    <td><input type="submit" value="<fmt:message key="entity.action.delete"/>" name="action"
                               class="input"></td>
                </tr>
                <tr>
                    <td><input type="text" placeholder="<fmt:message key="table.kind.column.id"/>"
                               name="id" id="id" class="input"></td>
                    <td><input type="text" placeholder="<fmt:message key="table.kind.column.kind"/>"
                               name="activity" id="activity" class="input"></td>
                </tr>
                <c:forEach var="kind" items="${requestScope.kinds}">
                    <tr>
                        <td><c:out value="${kind.id}"/></td>
                        <c:choose>
                            <c:when test="${sessionScope.lang eq 'en'}">
                                <td><c:out value="${kind.kindEn}"/></td>
                            </c:when>
                            <c:otherwise>
                                <td><c:out value="${kind.kindRu}"/></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </table>
    </div>
</div>
<hr/>
<custom:pagination/>
<%--            <div style="text-align: center; width:100%; background-color: #adb5bd">--%>
<%--                <c:if test="${sessionScope.pages.size() > 1}">--%>
<%--                    <c:forEach items="${sessionScope.pages}" var="item" varStatus="status">--%>
<%--                        <a href="${pageContext.request.contextPath}${item}">${status.count}</a>--%>
<%--                    </c:forEach>--%>
<%--                </c:if>--%>
<%--            </div>--%>
</body>
</html>
