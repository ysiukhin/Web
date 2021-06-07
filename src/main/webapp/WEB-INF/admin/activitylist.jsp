<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
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
    <label><h1>Hello ADMIN!</h1></label>
    <label><a href="${pageContext.request.contextPath}/logout">Logout</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=en&page=activityList&pagenumber=${sessionScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <img src="static/flags/gb.svg" width="16" height="16"/></span>ENGLISH</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=ru&page=activityList&pagenumber=${sessionScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <img src="static/flags/ru.svg" width="16" height="16"/>РУССКИЙ</a></label>
</div>
<br>
<hr>
<div class="vertical-menu">
    <a href="${pageContext.request.contextPath}/accountList?page=activityList"><fmt:message
            key="a.admin.get_accounts"/></a>
    <a href="${pageContext.request.contextPath}/activityList?page=activityList"><fmt:message
            key="a.admin.get_activities"/></a>
    <a href="${pageContext.request.contextPath}/kindList?page=activityList"><fmt:message
            key="a.admin.get_kinds"/></a>
    <a href="${pageContext.request.contextPath}/requestList?page=activityList"><fmt:message
            key="a.admin.get_requests"/></a>
    <a href="${pageContext.request.contextPath}/reportActivityList?page=activityList"><fmt:message
            key="a.admin.get_report_activity"/></a>
    <a href="${pageContext.request.contextPath}/reportAccountList?page=activityList"><fmt:message
            key="a.admin.get_report_account"/></a>
</div>
<div class="container">
    <div class="tab tab-1">
        <form action="http://localhost:8080/Web/activityAction" method="POST"
              onSubmit="return check('<fmt:message key="admin.activity.alert.message.empty"/>');">>
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
                    <td style="display:none;"><input type="text"
                                                     placeholder="<fmt:message key="table.activity.column.id"/>"
                                                     name="id" id="id" class="input"></td>
                    <td><input type="text" placeholder="<fmt:message key="table.activity.column.activity_en"/>"
                               name="activity_en" id="activity_en" class="input"></td>
                    <td><input type="text" placeholder="<fmt:message key="table.activity.column.activity_ru"/>"
                               name="activity_ru" id="activity_ru" class="input"></td>
                    <td>
                        <select name="activity_kind" id="activity_kind" class="input" style="width: 100%">
                            <option><fmt:message key="table.activity.column.activity_kind"/></option>
                            <c:forEach var="activity" items="${requestScope.activities}">
                                <option value="${activity.kindEn} | ${activity.kindRu}">${activity.kindEn}
                                    | ${activity.kindRu}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td hidden="hidden"><input type="text" placeholder="<fmt:message key="table.kind.column.id"/>"
                                               name="kind_id" id="kind_id" class="input"></td>
                </tr>
                <tr>
                    <th style="display:none;"><fmt:message key="table.activity.column.id"/></th>
                    <th><fmt:message key="table.activity.column.activity_en"/></th>
                    <th><fmt:message key="table.activity.column.activity_ru"/></th>
                    <th><fmt:message key="table.activity.column.activity_kind"/></th>
                    <th style="display:none;"><fmt:message key="table.kind.column.id"/></th>
                </tr>
                <c:forEach var="activity" items="${requestScope.activities}">
                    <tr class="tblrow">
                        <td style="display:none;"><c:out value="${activity.id}"/></td>
                        <td><c:out value="${activity.activityRu}"/></td>
                        <td><c:out value="${activity.activityEn}"/></td>
                        <td><c:out value="${activity.kindEn}"/> | <c:out value="${activity.kindRu}"/>
                        </td>
                        <td style="display:none;"><c:out value="${activity.kindId}"/></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="4"><custom:pagination/></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<hr/>
<script>
    <%@include file="../js/activity_list.js"%>
</script>
</body>
</html>
