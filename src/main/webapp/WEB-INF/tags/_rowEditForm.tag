<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>

<%--<%@ attribute name="account" required="true" type="ua.traning.rd.java.finalproject.core.model.Account" %>--%>
<%@ attribute name="actionCaption" required="true" type="java.lang.Boolean" %>
<%@ attribute name="actionStatus" required="true" type="java.lang.String" %>
<%@ attribute name="isAction" required="true" type="java.lang.Boolean" %>
<%--<%@ attribute name="totalCost" required="true" type="java.lang.Number"%>--%>
<%--<c:set scope="page" value="Ok" var="caption">--%>


<div id="window">
    <img class="close" src="${pageContext.request.contextPath}/WEB-INF/admin/close.png" alt="" onclick="show('none')">
    <div class="form">
        <c:choose>
            <c:when test="${actionCaption}">
                <h2><fmt:message key="entity.action.create"/>&nbsp;<fmt:message key="entity.action.result.ok"/></h2>
            </c:when>
            <c:otherwise>
                <h2><fmt:message key="entity.action.create"/>&nbsp;<fmt:message key="entity.action.result.bad"/></h2>
            </c:otherwise>
        </c:choose>
        <input type="button" onclick="show('none')"><fmt:message key="label.close"/>


        <%--        <form action="${pageContext.request.contextPath}/logout" name="f1" method="get">--%>
        <%--            <input type="text" placeholder="ИД" name="id" class="input">--%>
        <%--            <input type="text" placeholder="Имя" name="first_name" class="input">--%>
        <%--            <input type="text" placeholder="Фамилия" name="last_name" class="input">--%>
        <%--            <input type="email" placeholder="Отчество" name="middle_name" class="input">--%>
        <%--            <input type="email" placeholder="Емеил" name="email" class="input">--%>
        <%--            <input type="password" placeholder="Пароль" name="md5" class="input">--%>
        <%--            <input type="email" placeholder="Подтвердите адрес Электронной Почты" name="email2" class="input">--%>

        <%--            <input type="password" placeholder="Подтвердите пароль" name="pass2" class="input">--%>
        <%--            <input type='tel' placeholder="Мобильный телефон" name="tel" class="input">--%>
        <%--            <input type="radio" name="radio1"> Мужчина <input type="radio" name="radio1" class="radio"> Женщина--%>
        <%--            <input type="submit" value="Регистрация" name="sab" class="input"> Нажимая «Регистрация», вы подтверждаете,--%>
        <%--            что прочитали и согласны с нашими Условиями Пользования и Политикой Конфиденциальности.--%>
        <%--        </form>--%>
    </div>
</div>
<script>
    function show(state) {
        document.getElementById('idModalEditAccount').style.display = state;
        document.getElementById('gray').style.display = state;
    }
</script>
