<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">

<head>
    <title>ADMIN THE BASIS</title>
    <%--    <link href="admin.css" rel="stylesheet">--%>
    <style>
        header.page-header {
            /*background: no-repeat left/cover url(/media/examples/puppy-header-logo.jpg);*/
            display: flex;
            height: 120px;
            min-width: 120px;
            align-items: center;
            color: #c7c7c7;
            text-shadow: #000 0 0 .2em;
        }

        header.page-header > h1 {
            font: bold calc(1em + 2 * (100vw - 120px) / 100) 'Dancing Script', cursive,
            fantasy;
            margin: 2%;
            color: #f6f6f6;
        }

        main {
            font: 1rem 'Fira Sans', sans-serif;
        }

        .vertical-menu {
            width: 250px; /* Set a width if you like */
            float: left;
        }

        .vertical-menu a {
            background-color: #eee; /* Grey background color */
            color: black; /* Black text color */
            display: block; /* Make the links appear below each other */
            padding: 12px; /* Add some padding */
            text-decoration: none; /* Remove underline from links */
        }

        .vertical-menu a:hover {
            background-color: #ccc; /* Dark grey background on mouse-over */
        }

        .vertical-menu a.active {
            background-color: #878787; /* Add a green color to the "active/current" link */
            color: white;
        }

        table {
            border-collapse: collapse;
            border-spacing: 0;
            width: 60%;
            border: 1px solid #ddd;
        }

        th, td {
            text-align: left;
            padding: 16px;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #ccc; /* Dark grey background on mouse-over */
        }
    </style>
</head>

<body>
<div class="header-panel">
    <label><h1>Hello ADMIN!</h1></label>
    <label><a href="${pageContext.request.contextPath}/logout">Logout</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=en&page=activityList&pagenumber=${requestScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
        <span class="flag-icon flag-icon-gb"></span>ENGLISH</a></label>
    <label><a
            href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=ru&page=activityList&pagenumber=${requestScope.pagenumber}&rowsPerPage=${requestScope.rowsPerPage}">
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
<table>
    <tr>
        <th><fmt:message key="table.activity.column.id"/></th>
        <th><fmt:message key="table.activity.column.activity"/></th>
        <th><fmt:message key="table.activity.column.activity_kind"/></th>
        <th>
    </tr>
    <c:forEach var="activity" items="${requestScope.activities}">
        <tr>
            <th><c:out value="${activity.id}"/></th>
            <c:choose>
                <c:when test="${sessionScope.lang eq 'en'}">
                    <th><c:out value="${activity.activityRu}"/></th>
                </c:when>
                <c:otherwise>
                    <th><c:out value="${activity.activityEn}"/></th>
                </c:otherwise>
            </c:choose>
            <th><c:out value="${requestScope.kinds.get(activity.kindId - 1)}"/></th>
        </tr>
    </c:forEach>
</table>
<hr/>
<div style="text-align: center; width:100%; background-color: #adb5bd">
    <c:if test="${sessionScope.pages.size() > 1}">
        <c:forEach items="${sessionScope.pages}" var="item" varStatus="status">
            <a href="${pageContext.request.contextPath}${item}">${status.count}</a>
        </c:forEach>
    </c:if>
</div>
<hr/>
</body>
</html>
