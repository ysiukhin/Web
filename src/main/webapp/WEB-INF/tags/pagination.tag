<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%--<fmt:setLocale value="${sessionScope.lang}"/>--%>

<%--<%@ attribute name="accounts" required="true" type="java.util.Collection" %>--%>
<%--<%@ attribute name="totalCost" required="true" type="java.lang.Number"%>--%>
<%--<%@ attribute name="showActionColumn" required="true" type="java.lang.Boolean"%>--%>
<div style="text-align: center; width:100%; background-color: #bababa">
    <c:if test="${sessionScope.pages.size() > 1}">
        <c:forEach items="${sessionScope.pages}" var="item" varStatus="status">
            <a href="${pageContext.request.contextPath}${item}">${status.count}</a>
        </c:forEach>
    </c:if>
</div>
