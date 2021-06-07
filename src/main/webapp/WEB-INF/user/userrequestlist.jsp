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
    <custom:messageform actionStatus="${sessionScope.actionStatus}" actionMessage=""/>
    <c:set scope="session" var="isMessage" value="false"/>
</c:if>

<div class="header-panel">
    <label><h1>${sessionScope.account.account.firstName}&nbsp;${sessionScope.account.account.lastName}</h1></label>
    <label><h2>${sessionScope.account.account.email}</h2></label>
    <label><a href="${pageContext.request.contextPath}/logout">Logout</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=en&page=userRequestList&pagenumber=${sessionScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <span class="flag-icon flag-icon-gb"></span>ENGLISH</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=ru&page=userRequestList&pagenumber=${sessionScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <span class="flag-icon flag-icon-ru"></span>РУСКИЙ</a></label>

</div>
<br>
<hr>
<div class="vertical-menu">
    <a href="${pageContext.request.contextPath}/userTimer?page=userRequestList"><fmt:message
            key="a.user.get_set_activity_timer"/></a>
    <a href="${pageContext.request.contextPath}/userRequestList?page=userRequestList"><fmt:message
            key="a.user.request.action"/></a>
</div>
<div class="container">
    <div class="tab tab-1">
        <form action="http://localhost:8080/Web/userRequestAction" method="POST"
              onSubmit="return check('<fmt:message key="user.request.activity.alert.message.empty"/>');">
            <table class="table" id="table">
                <tr>
                    <td><input type="submit" value="<fmt:message key="user.report.request.action"/>" name="action"
                               class="input"></td>
                </tr>
                <tr>
                    <td hidden="hidden"><input type="text"
                                               placeholder="<fmt:message key="table.activity.column.activity_kind"/>"
                                               name="activity_kind" id="activity_kind" class="input"></td>
                    <td hidden="hidden"><input type="text"
                                               placeholder="<fmt:message key="table.request.column.activity"/>"
                                               name="activity" id="activity" class="input"></td>
                    <td hidden="hidden"><input type="text"
                                               placeholder="<fmt:message key="user.report.request.current_activity"/>"
                                               name="current_activity" id="current_activity" class="input"></td>
                    <td hidden="hidden"><input type="text"
                                               placeholder="<fmt:message key="user.report.request.current_request"/>"
                                               name="current_request" id="current_request" class="input"></td>
                    <td hidden="hidden"><input type="text" placeholder="activity_id" name="activity_id" id="activity_id"
                                               class="input"></td>
                    <td hidden="hidden"><input type="text" placeholder="account_activity_id" name="account_activity_id"
                                               id="account_activity_id" class="input"></td>
                    <td hidden="hidden"><input type="text" placeholder="request_id" name="request_id" id="request_id"
                                               class="input"></td>
                </tr>
                <tr>
                    <th><fmt:message key="table.activity.column.activity_kind"/></th>
                    <th><fmt:message key="table.request.column.activity"/></th>
                    <th><fmt:message key="user.report.request.current_activity"/></th>
                    <th><fmt:message key="user.report.request.current_request"/></th>
                    <th style="display:none;"><fmt:message key="table.activity.column.id"/></th>
                    <th style="display:none;"><fmt:message key="table.kind.column.id"/></th>
                    <th style="display:none;"><fmt:message key="table.kind.column.id"/></th>
                </tr>
                <c:forEach var="activity" items="${requestScope.activityList}" varStatus="status">
                    <tr class="tblrow">
                        <c:choose>
                            <c:when test="${sessionScope.lang eq 'en'}">
                                <td><c:out value="${activity.kindEn}"/></td>
                                <td><c:out value="${activity.activityEn}"/></td>
                            </c:when>
                            <c:otherwise>
                                <td><c:out value="${activity.kindRu}"/></td>
                                <td><c:out value="${activity.activityRu}"/></td>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${activity.accountActivityId eq 0}">
                                <td style="background: #EDEDED" ;></td>
                            </c:when>
                            <c:otherwise>
                                <td style="background: lightseagreen;"></td>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${activity.requestId eq 0}">
                                <td style="background: #EDEDED" ;></td>
                            </c:when>
                            <c:otherwise>
                                <td class="processed" style="background: lightseagreen;"></td>
                            </c:otherwise>
                        </c:choose>
                        <td style="display:none;"><c:out value="${activity.id}"/></td>
                        <td style="display:none;"><c:out value="${activity.accountActivityId}"/></td>
                        <td style="display:none;"><c:out value="${activity.requestId}"/></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="7"><custom:pagination/></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<hr/>
<script>
    <%@include file="../js/user_request_list.js"%>
</script>
</body>
</html>