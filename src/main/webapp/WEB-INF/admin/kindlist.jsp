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
    <label><h1>Hello ADMIN!</h1></label>
    <label><a href="${pageContext.request.contextPath}/logout">Logout</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=en&page=kindList&pagenumber=${sessionScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <span class="flag-icon flag-icon-gb"></span>ENGLISH</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=ru&page=kindList&pagenumber=${sessionScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <span class="flag-icon flag-icon-ru"></span>РУСКИЙ</a></label>
</div>
<br>
<hr>
<div class="vertical-menu">
    <a href="${pageContext.request.contextPath}/accountList?page=kindList"><fmt:message
            key="a.admin.get_accounts"/></a>
    <a href="${pageContext.request.contextPath}/activityList?page=kindList"><fmt:message
            key="a.admin.get_activities"/></a>
    <a href="${pageContext.request.contextPath}/kindList?page=kindList"><fmt:message
            key="a.admin.get_kinds"/></a>
    <a href="${pageContext.request.contextPath}/requestList?page=kindList"><fmt:message
            key="a.admin.get_requests"/></a>
    <a href="${pageContext.request.contextPath}/reportActivityList?page=kindList"><fmt:message
            key="a.admin.get_report_activity"/></a>
    <a href="${pageContext.request.contextPath}/reportAccountList?page=kindList"><fmt:message
            key="a.admin.get_report_account"/></a>
</div>
<div class="container">
    <div class="tab tab-1">
        <form action="http://localhost:8080/Web/kindAction" method="POST">
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
