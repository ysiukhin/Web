<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

</head>
<body>

<h1>Test Accounts list</h1><br/>

<h2>Все пользователи</h2><br/>

<c:forEach var="accounts" items="${requestScope.accounts}">
    <ul>
        <li>First Name: <c:out value="${accounts.firstName}"/></li>
        <li>Last Name: <c:out value="${accounts.lastName}"/></li>
        <li>Middle Name: <c:out value="${accounts.middleName}"/></li>
        <li>Email: <c:out value="${accounts.email}"/></li>
        <li>Login: <c:out value="${accounts.login}"/></li>
        <li>Password: <c:out value="${accounts.md5}"/></li>
        <li>Status: <c:out value="${accounts.status}"/></li>
        <li>RoleId: <c:out value="${accounts.roleId}"/></li>
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
