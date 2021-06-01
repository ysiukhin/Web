<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                    <td><input type="text" placeholder="<fmt:message key="table.account.column.id"/>" name="id" id="id"
                               class="input"></td>
                    <td><input type="text" placeholder="<fmt:message key="table.account.column.first_name"/>"
                               name="first_name" id="first_name" class="input"></td>
                    <td><input type="text" placeholder="<fmt:message key="table.account.column.last_name"/>"
                               name="last_name" id="last_name" class="input"></td>
                    <td><input type="text" placeholder="<fmt:message key="table.account.column.middle_name"/>"
                               name="middle_name" id="middle_name" class="input"></td>
                    <td><input type="email" placeholder="<fmt:message key="table.account.column.email"/>" name="email"
                               id="email" class="input"></td>
                    <td><input type="text" placeholder="<fmt:message key="table.account.column.md5"/>" name="md5"
                               id="md5" class="input"></td>
                </tr>
            </table>
        </form>
    </div>
    <div class="tab tab-1">
        <table id="table" border="1">
            <tr>
                <td><fmt:message key="table.account.column.id"/></td>
                <td><fmt:message key="table.account.column.first_name"/></td>
                <td><fmt:message key="table.account.column.last_name"/></td>
                <td><fmt:message key="table.account.column.middle_name"/></td>
                <td><fmt:message key="table.account.column.email"/></td>
                <td><fmt:message key="table.account.column.md5"/></td>
                <%--                <td><fmt:message key="entity.action"/></td>--%>
            </tr>
            <c:forEach var="accounts" items="${requestScope.accounts}">
                <tr class="tblrow">
                    <td><c:out value="${accounts.id}"/></td>
                    <td><c:out value="${accounts.firstName}"/></td>
                    <td><c:out value="${accounts.lastName}"/></td>
                    <td><c:out value="${accounts.middleName}"/></td>
                    <td><c:out value="${accounts.email}"/></td>
                    <td><c:out value="${accounts.md5}"/></td>
                        <%--                    <td><fmt:message key="entity.action.delete"/></td>--%>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<hr/>
<custom:pagination/>
<%--<div style="text-align: center; width:100%; background-color: #adb5bd">--%>
<%--    <c:if test="${sessionScope.pages.size() > 1}">--%>
<%--        <c:forEach items="${sessionScope.pages}" var="item" varStatus="status">--%>
<%--            <a href="${pageContext.request.contextPath}${item}">${status.count}</a>--%>
<%--        </c:forEach>--%>
<%--    </c:if>--%>
<%--</div>--%>
<hr/>
<script>
    <%@include file="/static/js/account_list.js"%>
</script>
</body>
</html>
