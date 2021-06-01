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
                    <td><input type="text" placeholder="<fmt:message key="table.request.column.id"/>" name="id" id="id"
                               class="input"></td>
                    <td><input type="text" placeholder="<fmt:message key="table.request.column.request"/>"
                               name="request" id="request" class="input"></td>
                    <td><input type="text" placeholder="<fmt:message key="table.request.column.account_id"/>"
                               name="account_id" id="account_id" class="input"></td>
                    <td><input type="text" placeholder="<fmt:message key="table.request.column.account_first_name"/>"
                               name="account_first_name" id="account_first_name" class="input"></td>
                    <td><input type="text" placeholder="<fmt:message key="table.request.column.account_last_name"/>"
                               name="account_last_name" id="account_last_name" class="input"></td>
                    <td><input type="text" placeholder="<fmt:message key="table.request.column.activity_id"/>"
                               name="activity_id" id="activity_id" class="input"></td>
                    <td><input type="text" placeholder="<fmt:message key="table.request.column.activity"/>"
                               name="activity" id="activity" class="input"></td>

                    <%--        <th><fmt:message key="table.request.column.id"/></th>--%>
                    <%--        <th><fmt:message key="table.request.column.request"/></th>--%>
                    <%--        <th><fmt:message key="table.request.column.account_id"/></th>--%>
                    <%--        <th><fmt:message key="table.request.column.account_first_name"/></th>--%>
                    <%--        <th><fmt:message key="table.request.column.account_last_name"/></th>--%>
                    <%--        <th><fmt:message key="table.request.column.activity_id"/></th>--%>
                    <%--        <th><fmt:message key="table.request.column.activity"/></th>--%>
                    <%--        <th><fmt:message key="entity.action"/></th>--%>
                    <%--        <th>--%>
                </tr>
                <c:forEach var="list" items="${requestScope.resultList}">
                    <tr>
                        <th><c:out value="${list.request.id}"/></th>
                        <c:choose>
                            <c:when test="${list.request.request}">
                                <th><fmt:message key="table.request.request.message.true"/></th>
                            </c:when>
                            <c:otherwise>
                                <th><fmt:message key="table.request.request.message.false"/></th>
                            </c:otherwise>
                        </c:choose>
                        <th><c:out value="${list.account.id}"/></th>
                        <th><c:out value="${list.account.firstName}"/></th>
                        <th><c:out value="${list.account.lastName}"/></th>
                        <th><c:out value="${list.activity.id}"/></th>
                        <c:choose>
                            <c:when test="${sessionScope.lang eq 'en'}">
                                <th><c:out value="${list.activity.activityRu}"/></th>
                            </c:when>
                            <c:otherwise>
                                <th><c:out value="${list.activity.activityEn}"/></th>
                            </c:otherwise>
                        </c:choose>
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
</body>
</html>