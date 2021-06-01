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
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=en&page=requestList&pagenumber=${requestScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <span class="flag-icon flag-icon-gb"></span>ENGLISH</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=ru&page=requestList&pagenumber=${requestScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
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
        <form action="http://localhost:8080/Web/accountAction" method="POST">
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
                    <%--                    <td><input type="text" placeholder="<fmt:message key="table.request.column.id"/>" name="id" id="id"--%>
                    <%--                               class="input"></td>--%>
                    <td><input type="text" placeholder="<fmt:message key="table.request.column.request"/>"
                               name="request" id="request" class="input"></td>
                    <%--                    <td><input type="text" placeholder="<fmt:message key="table.request.column.account_id"/>"--%>
                    <%--                               name="account_id" id="account_id" class="input"></td>--%>
                    <td><input type="text" placeholder="<fmt:message key="table.request.column.account_first_name"/>"
                               name="account_first_name" id="account_first_name" class="input"></td>
                    <td><input type="text" placeholder="<fmt:message key="table.request.column.account_last_name"/>"
                               name="account_last_name" id="account_last_name" class="input"></td>
                    <%--                    <td><input type="text" placeholder="<fmt:message key="table.request.column.activity_id"/>"--%>
                    <%--                               name="activity_id" id="activity_id" class="input"></td>--%>
                    <td><input type="text" placeholder="<fmt:message key="table.request.column.activity"/>"
                               name="activity" id="activity" class="input"></td>
                </tr>
            </table>
        </form>
    </div>
    <div class="tab tab-1">
        <table id="table" border="1">
            <tr>
                <%--                            <td><fmt:message key="table.request.column.id"/></td>--%>
                <td><fmt:message key="table.request.column.request"/></td>
                <%--                            <td><fmt:message key="table.request.column.account_id"/></td>--%>
                <td><fmt:message key="table.request.column.account_first_name"/></td>
                <td><fmt:message key="table.request.column.account_last_name"/></td>
                <%--                            <td><fmt:message key="table.request.column.activity_id"/></td>--%>
                <td><fmt:message key="table.request.column.activity"/></td>
            </tr>
            <c:forEach var="list" items="${requestScope.resultList}">
                <tr>
                        <%--                        <th><c:out value="${list.request.id}"/></th>--%>
                    <c:choose>
                        <c:when test="${list.request.request}">
                            <td><fmt:message key="table.request.request.message.true"/></td>
                        </c:when>
                        <c:otherwise>
                            <td><fmt:message key="table.request.request.message.false"/></td>
                        </c:otherwise>
                    </c:choose>
                        <%--                        <th><c:out value="${list.account.id}"/></th>--%>
                    <td><c:out value="${list.account.firstName}"/></td>
                    <td><c:out value="${list.account.lastName}"/></td>
                        <%--                        <th><c:out value="${list.activity.id}"/></th>--%>
                    <c:choose>
                        <c:when test="${sessionScope.lang eq 'en'}">
                            <td><c:out value="${list.activity.activityRu}"/></td>
                        </c:when>
                        <c:otherwise>
                            <td><c:out value="${list.activity.activityEn}"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<hr/>
<custom:pagination/>
<script>
    <%@include file="/static/js/request_list.js"%>
</script>
</body>
</html>