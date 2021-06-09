<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import="ua.traning.rd.java.finalproject.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="${Constants.MESSAGES_BUNDLE}"/>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Doctor App</title>
</head>
<body>
<div>
    <h2><span>404</span></h2>
    <label><fmt:message key="label.error404"/></label>
    <p><fmt:message key="label.author"/></p>
</div>
</body>
</html>