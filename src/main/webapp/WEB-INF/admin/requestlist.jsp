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
        <%@include file="../css/admin.css"%>
        <%@include file="../css/messageform.css"%>
        <%@include file="../css/list.css"%>
        <%@include file="../css/pagination.css"%>
        <%@include file="../css/table.css"%>
    </style>
</head>

<body>
<c:if test="${sessionScope.isMessage}">
    <c:set scope="session" var="isMessage" value="false"/>
    <custom:messageform actionStatus="${sessionScope.actionStatus}" actionMessage="${sessionScope.actionMessage}"/>
    <%--    <c:set scope="session" var="isMessage" value="false"/>--%>
</c:if>

<div class="header-panel">
    <h1>Hello ADMIN!</h1>
    <label><a href="${pageContext.request.contextPath}/logout">Logout</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=en&page=requestList&pagenumber=${sessionScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <span class="flag-icon flag-icon-gb"></span>ENGLISH</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=ru&page=requestList&pagenumber=${sessionScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <span class="flag-icon flag-icon-ru"></span>РУСКИЙ</a></label>

</div>
<br>
<hr>
<div class="vertical-menu">
    <a href="${pageContext.request.contextPath}/accountList?page=requestList"><fmt:message
            key="a.admin.get_accounts"/></a>
    <a href="${pageContext.request.contextPath}/activityList?page=requestList"><fmt:message
            key="a.admin.get_activities"/></a>
    <a href="${pageContext.request.contextPath}/kindList?page=requestList"><fmt:message
            key="a.admin.get_kinds"/></a>
    <a href="${pageContext.request.contextPath}/requestList?page=requestList"><fmt:message
            key="a.admin.get_requests"/></a>
    <a href="${pageContext.request.contextPath}/reportActivityList?page=requestList"><fmt:message
            key="a.admin.get_report_activity"/></a>
    <a href="${pageContext.request.contextPath}/reportAccountList?page=requestList"><fmt:message
            key="a.admin.get_report_account"/></a>
</div>
<div class="container">
    <div class="tab tab-1">
        <form action="http://localhost:8080/Web/requestAction" method="POST">
            <table class="table" id="table">
                <tr>
                    <td><input type="submit" value="<fmt:message key="entity.action.approve"/>" name="action"
                               class="input"></td>
                    <td><input type="submit" value="<fmt:message key="entity.action.denied"/>" name="action"
                               class="input"></td>
                </tr>
                <tr hidden="hidden">
                    <td>
                        <input type="text" placeholder="<fmt:message key="table.request.column.id"/>" name="id" id="id"
                               class="input">
                    </td>
                    <td>
                        <input type="text" readonly="readonly"
                               placeholder="<fmt:message key="table.request.column.request"/>" name="request"
                               id="request" class="input">
                    </td>
                    <td>
                        <input type="text" readonly="readonly"
                               placeholder="<fmt:message key="table.request.column.account_id"/>" name="account_id"
                               id="account_id" class="input">
                    </td>
                    <td>
                        <input readonly="readonly" type="text"
                               placeholder="<fmt:message key="table.request.column.account_first_name"/>"
                               name="account_first_name" id="account_first_name" class="input">
                    </td>
                    <td>
                        <input readonly="readonly" type="text"
                               placeholder="<fmt:message key="table.request.column.account_last_name"/>"
                               name="account_last_name" id="account_last_name" class="input">
                    </td>
                    <td hidden="hidden">
                        <input type="text" placeholder="<fmt:message key="table.request.column.activity_id"/>"
                               name="activity_id" id="activity_id" class="input">
                    </td>
                    <td>
                        <input readonly="readonly" type="text"
                               placeholder="<fmt:message key="table.request.column.activity"/>" name="activity"
                               id="activity" class="input">
                    </td>
                    <td>
                        <input readonly="readonly" type="text"
                               placeholder="<fmt:message key="table.request.column.status"/>" name="status" id="status"
                               class="input">
                    </td>
                    <td>
                        <input type="text" placeholder="<fmt:message key="table.account.column.email"/>"
                               name="email" id="email" class="input">
                    </td>
                </tr>
                <tr>
                    <th style="display:none;"><fmt:message key="table.request.column.id"/></th>
                    <%--                    <th><fmt:message key="table.request.column.request"/></th>--%>
                    <th style="display:none;"><fmt:message key="table.account.column.id"/></th>
                    <th><fmt:message key="table.account.column.first_name"/></th>
                    <th><fmt:message key="table.account.column.last_name"/></th>
                    <th style="display:none;"><fmt:message key="table.activity.column.id"/></th>
                    <th><fmt:message key="table.request.column.activity"/></th>
                    <th><fmt:message key="table.request.column.request"/></th>
                    <%--                    list.account.id list.account.firstName list.account.lastName list.activity.id list.activity.activityRu--%>
                    <%--                    list.activity.activityEn list.request.request list.account.email--%>

                    <th style="display:none;"><fmt:message key="table.account.column.email"/></th>
                </tr>
                <c:forEach var="list" items="${requestScope.resultList}">
                    <tr class="tblrow">
                        <td style="display:none;"><c:out value="${list.requestId}"/></td>
                        <td style="display:none;"><c:out value="${list.accountId}"/></td>
                        <td><c:out value="${list.firstName}"/></td>
                        <td><c:out value="${list.lastName}"/></td>
                        <td style="display:none;"><c:out value="${list.activityId}"/></td>
                        <c:choose>
                            <c:when test="${sessionScope.lang eq 'en'}">
                                <td><c:out value="${list.activityRu}"/></td>
                            </c:when>
                            <c:otherwise>
                                <td><c:out value="${list.activityEn}"/></td>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${list.request == 1}">
                                <td><fmt:message key="table.request.request.message.true"/></td>
                            </c:when>
                            <c:otherwise>
                                <td><fmt:message key="table.request.request.message.false"/></td>
                            </c:otherwise>
                        </c:choose>
                        <td style="display:none;"><c:out value="${list.email}"/></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="8"><custom:pagination/></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<hr/>
<script>
    <%@include file="../js/request_list.js"%>
</script>
</body>
</html>