<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

</head>
<body>

<h1>Test Accounts list</h1><br/>

<h2>Все пользователи</h2><br/>

<c:forEach var="account" items="${requestScope.accounts}">
    <ul>
        <li>First Name: <c:out value="${account.firstName}"/></li>
        <li>Last Name: <c:out value="${account.lastName}"/></li>
        <li>Middle Name: <c:out value="${account.middleName}"/></li>
        <li>Email: <c:out value="${account.email}"/></li>
        <li>Login: <c:out value="${account.login}"/></li>
        <li>Password: <c:out value="${account.md5}"/></li>
        <li>Status: <c:out value="${account.status}"/></li>
        <li>RoleId: <c:out value="${account.roleId}"/></li>
    </ul>
    <hr/>
</c:forEach>

<h2>Создание нового пользователя</h2><br/>

<form method="post" action="">
    <label><input type="text" name="firstName"></label>firstName<br>
    <label><input type="text" name="lastName"></label>lastName<br>
    <label><input type="text" name="middleName"></label>middleName<br>
    <label><input type="text" name="email"></label>email<br>
    <label><input type="text" name="login"></label>login<br>
    <label><input type="password" name="password"></label>password<br>
    <label><input type="number" name="status"></label>status<br>
    <label><input type="number" name="roleId"></label>roleId<br>
    <input type="submit" value="Ok" name="Ok"><br>
</form>

</body>
</html>
