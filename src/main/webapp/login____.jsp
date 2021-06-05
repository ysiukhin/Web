<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>

</head>
<body>

<div class="form">

    <h1>Вход в систему</h1><br>
    <a href="${pageContext.request.contextPath}/logout">На головну</a>
    <form method="post" action="${pageContext.request.contextPath}/login">

        <input required placeholder="login" name="email" type="email"><br>
        <input type="password" required placeholder="password" name="password"><br><br>
        <input class="button" type="submit" value="Войти">

    </form>
</div>
</body>
</html>