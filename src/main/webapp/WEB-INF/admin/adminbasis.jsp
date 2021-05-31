<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" uri="/WEB-INF/taglib.tld" %>

<%@ page isELIgnored="false" %>


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<custom:cacheOff/>

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
            width: 200px; /* Set a width if you like */
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
    </style>
</head>
<header class="page-header">
    <div class="header-panel">
        <label><h1>Hello ADMIN!</h1></label>
        <label><a href="${pageContext.request.contextPath}/logout">Logout</a></label>
        <label><a href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=en&page=adminsection"
        <%--        <label><a href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=en&page=adminbasis.jsp"--%>
                  class="link-secondary">
            <span class="flag-icon flag-icon-gb"></span>ENGLISH</a></label>
        <label><a href="${pageContext.request.contextPath}/changeLanguage?sessionLocale=ru&page=adminsection"
                  class="link-secondary">
            <span class="flag-icon flag-icon-ru"></span>РУСКИЙ</a></label>
    </div>
</header>
<main>
    <hr>
    <div class="vertical-menu">
        <%--        <a href="#" class="active">Home</a>--%>
        <a href="${pageContext.request.contextPath}/accountList"><fmt:message key="a.admin.get_accounts"/></a>
        <a href="${pageContext.request.contextPath}/kindList"><fmt:message key="a.admin.get_kinds"/></a>
        <a href="${pageContext.request.contextPath}/activityList"><fmt:message key="a.admin.get_activities"/></a>
        <a href="${pageContext.request.contextPath}/requestList"><fmt:message key="a.admin.get_requests"/></a>
        <a href="#"><fmt:message key="a.admin.get_requests"/></a>
    </div>
    <hr>
</main>
<body>
</body>
</html>