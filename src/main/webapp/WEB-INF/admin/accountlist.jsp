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
        <%@include file="../css/admin.css"%>
        <%@include file="../css/messageform.css"%>
        <%@include file="../css/list.css"%>
        <%@include file="../css/pagination.css"%>
        <%@include file="../css/table.css"%>
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
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=en&page=accountList&pagenumber=${sessionScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <span class="flag-icon flag-icon-gb"></span>ENGLISH</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=ru&page=accountList&pagenumber=${sessionScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <span class="flag-icon flag-icon-ru"></span>РУСКИЙ</a></label>
    <br>
    <hr>
    <div class="vertical-menu">
        <a href="${pageContext.request.contextPath}/accountList?page=accountList"><fmt:message
                key="a.admin.get_accounts"/></a>
        <a href="${pageContext.request.contextPath}/activityList?page=accountList"><fmt:message
                key="a.admin.get_activities"/></a>
        <a href="${pageContext.request.contextPath}/kindList?page=accountList"><fmt:message
                key="a.admin.get_kinds"/></a>
        <a href="${pageContext.request.contextPath}/requestList?page=accountList"><fmt:message
                key="a.admin.get_requests"/></a>
        <a href="${pageContext.request.contextPath}/reportActivityList?page=accountList"><fmt:message
                key="a.admin.get_report_activity"/></a>
        <a href="${pageContext.request.contextPath}/reportAccountList?page=accountList"><fmt:message
                key="a.admin.get_report_account"/></a>
    </div>

    <div class="container">
        <div class="tab tab-1">
            <form action="http://localhost:8080/Web/accountAction" method="POST"
                  onSubmit="return check('<fmt:message key="admin.account.alert.message.empty"/>');">
                <table class="table" id="table">
                    <tr>
                        <td><input type="submit" value="<fmt:message key="entity.action.create"/>" name="action"
                                   class="input"></td>
                        <td><input type="submit" value="<fmt:message key="entity.action.update"/>" name="action"
                                   class="input"></td>
                        <td><input type="submit" value="<fmt:message key="entity.action.delete"/>" name="action"
                                   class="input"></td>
                        <%--                    <td><input type="hidden" value="accountList" name="page" class="input"></td>--%>
                    </tr>
                <tr>
                    <td style="display:none;"><input type="text"
                                                     placeholder="<fmt:message key="table.account.column.id"/>"
                                                     name="id" id="id" class="input"></td>
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
                <tr>
                    <th style="display:none;"><fmt:message key="table.account.column.id"/></th>
                    <th><fmt:message key="table.account.column.first_name"/></th>
                    <th><fmt:message key="table.account.column.last_name"/></th>
                    <th><fmt:message key="table.account.column.middle_name"/></th>
                    <th><fmt:message key="table.account.column.email"/></th>
                    <th><fmt:message key="table.account.column.md5"/></th>
                </tr>
                <c:forEach var="accounts" items="${requestScope.accounts}">
                    <tr class="tblrow">
                        <td style="display:none;"><c:out value="${accounts.id}"/></td>
                        <td><c:out value="${accounts.firstName}"/></td>
                        <td><c:out value="${accounts.lastName}"/></td>
                        <td><c:out value="${accounts.middleName}"/></td>
                        <td><c:out value="${accounts.email}"/></td>
                        <td style="display:none;"><c:out value="${accounts.md5}"/></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="6"><custom:pagination/></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<hr/>

<hr/>
<script>
    <%@include file="../js/account_list.js"%>
</script>
</body>
</html>
