<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>"${title}" PAGE</title>
    <link href="${pageContext.request.contextPath}/static/bootstrap-5.0.1/css/bootstrap.css" rel="stylesheet">
</head>
<jsp:include page="${currentPage}"/>
</html>
<script src="${pageContext.request.contextPath}/static/bootstrap-5.0.1/js/bootstrap.js"></script>