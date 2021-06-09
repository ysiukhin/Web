<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import="ua.traning.rd.java.finalproject.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tag" uri="/WEB-INF/taglib.tld" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="${Constants.MESSAGES_BUNDLE}"/>

<!doctype html>
<html lang="${sessionScope.lang}">

<head>
    <title>ADMIN SECTION</title>
    <style>
        <%@include file="../css/admin.css"%>
        <%@include file="../css/messageform.css"%>
        <%@include file="../css/list.css"%>
        <%@include file="../css/pagination.css"%>
        <%@include file="../css/table.css"%>
    </style>
    <tag:cacheOff/>
</head>

<body>
<c:if test="${sessionScope.isMessage}">
    <c:set scope="session" var="${Constants.IS_MESSAGE_TO_SHOW}" value="false"/>
    <custom:messageform actionStatus="${sessionScope.actionStatus}"
                        actionCaption="${sessionScope.actionCaption}"
                        actionMessage="${sessionScope.actionMessage}"/>
</c:if>
<div class="header-panel">
    <label><h1>Hello ADMIN!</h1></label>
    <label><a href="${pageContext.request.contextPath}${Constants.COMMAND_LOGOUT}">Logout</a></label>
    <label><a
            href="${pageContext.request.contextPath}${Constants.COMMAND_CHANGE_LANGUAGE}?sessionLocale=${Constants.LOCALE_ENGLISH}&${Constants.PAGE}=${Constants.COMMAND_ADMIN_KIND_LIST}&${Constants.PAGE_NUMBER}=${sessionScope.pagenumber}&${Constants.ROWS_PER_PAGE}=${requestScope.rowsPerPage}">
        <img src="${pageContext.request.contextPath}/static/flags/gb.svg" width="16" height="16"/>ENGLISH</a></label>
    <label><a
            href="${pageContext.request.contextPath}${Constants.COMMAND_CHANGE_LANGUAGE}?sessionLocale=${Constants.LOCALE_RUSSIAN}&${Constants.PAGE}=${Constants.COMMAND_ADMIN_KIND_LIST}&${Constants.PAGE_NUMBER}=${sessionScope.pagenumber}&${Constants.ROWS_PER_PAGE}=${requestScope.rowsPerPage}">
        <img src="${pageContext.request.contextPath}/static/flags/ru.svg" width="16" height="16"/>РУССКИЙ</a></label>
</div>
<br>
<hr>
<div class="vertical-menu">
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_CHANGE_LANGUAGE}?${Constants.PAGE}=${Constants.COMMAND_ADMIN_KIND_LIST}"><fmt:message
            key="a.admin.get_accounts"/></a>
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_ACTIVITY_LIST}?${Constants.PAGE}=${Constants.COMMAND_ADMIN_KIND_LIST}"><fmt:message
            key="a.admin.get_activities"/></a>
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_KIND_LIST}?${Constants.PAGE}=${Constants.COMMAND_ADMIN_KIND_LIST}"><fmt:message
            key="a.admin.get_kinds"/></a>
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_REQUEST_LIST}?${Constants.PAGE}=${Constants.COMMAND_ADMIN_KIND_LIST}"><fmt:message
            key="a.admin.get_requests"/></a>
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_REPORT_REQUEST_LIST}?${Constants.PAGE}=${Constants.COMMAND_ADMIN_KIND_LIST}"><fmt:message
            key="a.admin.get_report_activity"/></a>
    <a href="${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_REPORT_ACCOUNT_LIST}?${Constants.PAGE}=${Constants.COMMAND_ADMIN_KIND_LIST}"><fmt:message
            key="a.admin.get_report_account"/></a>
</div>
<div class="container">
    <div class="tab tab-1">
        <form action="http://localhost:8080${pageContext.request.contextPath}${Constants.COMMAND_ADMIN_KIND_ACTION}"
              method="POST"
              onSubmit="return check('<fmt:message key="admin.kind.alert.message.empty"/>');">
            <table class="table" id="table">
                <tr>
                    <td><input type="submit" value="<fmt:message key="entity.action.create"/>" name="action"
                               class="input"></td>
                    <td><input type="submit" value="<fmt:message key="entity.action.update"/>" name="action"
                               class="input"></td>
                    <td><input type="submit" value="<fmt:message key="entity.action.delete"/>" name="action"
                               class="input"></td>
                </tr>
                <tr>
                    <td style="display:none;"><input type="text" placeholder="<fmt:message key="table.kind.column.id"/>"
                                                     name="id" id="id" class="input"></td>
                    <td><input type="text" placeholder="<fmt:message key="table.kind.column.kind_en"/>"
                               name="kind_en" id="kind_en" class="input"></td>
                    <td><input type="text" placeholder="<fmt:message key="table.kind.column.kind_ru"/>"
                               name="kind_ru" id="kind_ru" class="input"></td>
                </tr>
                <tr>
                    <th style="display:none;"><fmt:message key="table.kind.column.id"/></th>
                    <th><fmt:message key="table.kind.column.kind_en"/></th>
                    <th><fmt:message key="table.kind.column.kind_ru"/></th>
                </tr>
                <c:forEach var="kind" items="${requestScope.kinds}">
                    <tr class="tblrow">
                        <td style="display:none;"><c:out value="${kind.id}"/></td>
                            <%--                        <c:choose>--%>
                            <%--                            <c:when test="${sessionScope.lang eq 'en'}">--%>
                        <td><c:out value="${kind.kindEn}"/></td>
                            <%--                            </c:when>--%>
                            <%--                            <c:otherwise>--%>
                        <td><c:out value="${kind.kindRu}"/></td>
                            <%--                            </c:otherwise>--%>
                            <%--                        </c:choose>--%>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="2"><custom:pagination/></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<hr/>
<script>
    <%@include file="../js/kind_list.js" %>
</script>
</body>
</html>
