<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<%--<%@ attribute name="account" required="true" type="ua.traning.rd.java.finalproject.core.model.Account" %>--%>
<%@ attribute name="actionStatus" required="true" type="java.lang.Boolean" %>
<%@ attribute name="actionMessage" required="false" type="java.lang.String" %>

<div onclick="show('none')" id="gray">
    <div id="window">
<%--        <img class="close" src="${pageContext.request.contextPath}/static/close.png" alt="" onclick="show('none')">--%>
        <div class="form">
            <c:choose>
                <c:when test="${actionStatus}">
                    <h2><fmt:message key="entity.dao.operation"/>&nbsp;<fmt:message key="entity.action.result.ok"/></h2>
                </c:when>
                <c:otherwise>
                    <h2><fmt:message key="entity.action.create"/>&nbsp;<fmt:message
                            key="entity.action.result.bad"/></h2>
                </c:otherwise>
            </c:choose>
            <label><c:out value="${actionMessage}"/></label>
            <input type="button" onclick="show('none')"><fmt:message key="label.close"/>
        </div>
    </div>
</div>
<script>
    function show(state) {
        document.getElementById('window').style.display = state;
        document.getElementById('gray').style.display = state;
    }
</script>
