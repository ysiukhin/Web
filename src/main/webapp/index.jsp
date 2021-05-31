<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*, java.text.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<%!
    String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        return sdf.format(new Date());
    }
%>

<html lang="${sessionScope.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title}</title>
</head>
<body>
<div class="">
    <label><a href="index.jsp?sessionLocale=en" class="link-secondary"><span
            class="flag-icon flag-icon-gb"></span>ENGLISH</a></label>
    <label><a href="index.jsp?sessionLocale=ru" class="link-secondary"><span
            class="flag-icon flag-icon-ru"></span>РУСКИЙ</a></label>
    <h2>
        <i>Сегодня <%= getFormattedDate() %>
        </i>
</h2>

    <br/>
<a href="${pageContext.request.contextPath}/login.jsp"><fmt:message key="button.sign_in"/></a>
<%--<br>--%>
<%--<a href="${pageContext.request.contextPath}/exception">Exception</a>--%>
<%--<br>--%>
</div>
</body>
</html>
