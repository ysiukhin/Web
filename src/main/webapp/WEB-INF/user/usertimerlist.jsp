<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import="ua.traning.rd.java.finalproject.Constants" %>
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
    <title>USER section</title>
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
    <custom:messageform actionStatus="${sessionScope.actionStatus}"
                        actionCaption="${sessionScope.actionCaption}"
                        actionMessage="${sessionScope.actionMessage}"/>
</c:if>

<div class="header-panel">
    <label><h1>${sessionScope.account.account.firstName}&nbsp;${sessionScope.account.account.lastName}</h1></label>
    <label><h2>${sessionScope.account.account.email}</h2></label>
    <label><a href="${pageContext.request.contextPath}/${Constants.COMMAND_LOGOUT}">Logout</a></label>
    <label><a
            href="${pageContext.request.contextPath}${Constants.COMMAND_LOGOUT}?${Constants.SESSION_LOCALE}=${Constants.LOCALE_ENGLISH}&${Constants.PAGE}=${Constants.COMMAND_USER_TIMER}&${Constants.PAGE_NUMBER}=${sessionScope.pagenumber}&${Constants.ROWS_PER_PAGE}=${requestScope.rowsPerPage}">
        <img src="${pageContext.request.contextPath}/static/flags/gb.png" width="20" height="16"/>ENGLISH</a></label>
    <label><a
            href="${pageContext.request.contextPath}${Constants.COMMAND_LOGOUT}?${Constants.SESSION_LOCALE}=${Constants.LOCALE_RUSSIAN}&${Constants.PAGE}=${Constants.COMMAND_USER_TIMER}&${Constants.PAGE_NUMBER}=${sessionScope.pagenumber}&${Constants.ROWS_PER_PAGE}=${requestScope.rowsPerPage}">
        <img src="${pageContext.request.contextPath}/static/flags/ru.png" width="20" height="16"/>РУССКИЙ</a></label>

</div>
<br>
<hr>
<div class="vertical-menu">
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_USER_TIMER}?${Constants.PAGE}=${Constants.COMMAND_USER_TIMER}"><fmt:message
            key="a.user.get_set_activity_timer"/></a>
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_USER_REQUEST_LIST}?${Constants.PAGE}=${Constants.COMMAND_USER_TIMER}"><fmt:message
            key="a.user.request.action"/></a>
</div>

<c:if test="${requestScope.timerActivityList.get(0).recordId > 0 }" var="flag" scope="request"/>
<%--<c:out value="${requestScope.flag}"/>--%>
<div class="container">
    <div class="tab tab-1">
        <form action="http://localhost:8080${pageContext.request.contextPath}${Constants.COMMAND_USER_TIMER_ACTION}"
              method="POST"
              onSubmit="return check('<fmt:message key="user.timer.alert.message.empty"/>');">
            <table class="table" id="table">
                <tr>
                    <c:choose>
                        <c:when test="${requestScope.flag}">
                            <td><input type="submit" value="<fmt:message key="user.timer.end.button"/>" name="action"
                                       class="input"></td>
                        </c:when>
                        <c:otherwise>
                            <td><input type="submit"
                                       value="<fmt:message key="user.timer.start.button"/>" name="action"
                                       class="input"></td>
                        </c:otherwise>
                    </c:choose>

                </tr>
                <tr>
                    <td hidden="hidden"><input type="text" name="account_activity_id" id="account_activity_id"
                                               class="input"></td>
                    <td hidden="hidden"><input type="text" name="activity_kind" id="activity_kind" class="input"></td>
                    <td hidden="hidden"><input type="text" name="activity" id="activity" class="input"></td>
                    <td hidden="hidden"><input type="text" name="timer_start" id="timer_start" class="input"></td>
                    <td hidden="hidden"><input type="text" name="record" id="record" class="input"
                    <c:if test="${requestScope.flag}">
                        <c:out value="value=${requestScope.timerActivityList.get(0).recordId}"/>
                    </c:if> >
                    </td>

                </tr>
                <tr>
                    <th><fmt:message key="table.activity.column.activity_kind"/></th>
                    <th><fmt:message key="table.request.column.activity"/></th>
                    <c:if test="${requestScope.flag}">
                        <th><fmt:message key="user.timer.start.label"/></th>
                    </c:if>
                </tr>
                <c:forEach var="activity" items="${requestScope.timerActivityList}" varStatus="status">
                    <c:choose>
                        <c:when test="${requestScope.flag}">
                            <tr class="tblrow processed">
                        </c:when>
                        <c:otherwise>
                            <tr class="tblrow">
                        </c:otherwise>
                    </c:choose>
                    <td style="display:none;"><c:out value="${activity.accountActivityId}"/></td>
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
                        <c:when test="${requestScope.flag}">
                            <td><c:out value="${activity.start}"/></td>
                        </c:when>
                        <c:otherwise>
                            <td style="display:none;"><c:out value="${activity.start}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td style="display:none;"><c:out value="${activity.recordId}"/></td>
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
    <%@include file="../js/user_timer_list.js"%>
</script>
</body>
</html>