<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="text-align: center; width:100%; background-color: #bababa">
    <c:if test="${sessionScope.pages.size() > 1}">
        <c:if test="${sessionScope.pagenumber} > 1}">
            <a href="${pageContext.request.contextPath}${sessionScope.pages.get(sessionScope.pagenumber -2)}"
               class="pagination previous round">‹</a>
        </c:if>
        <c:if test="${sessionScope.pagenumber == 1}">
            <a style="pointer-events: none;cursor: default; text-decoration: none;"
               href="${pageContext.request.contextPath}${sessionScope.pages.get(0)}"
               class="pagination previous round">‹</a>
        </c:if>
        <c:forEach items="${sessionScope.pages}" var="item" varStatus="status">
            <c:choose>
                <c:when test="${status.count eq sessionScope.pagenumber}">
                    <a style="background-color: red; color: #fff;"
                       href="${pageContext.request.contextPath}${item}">${status.count}&nbsp;</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}${item}">${status.count}&nbsp;</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${sessionScope.pagenumber < sessionScope.pages.size()}">
            <a href="${pageContext.request.contextPath}${sessionScope.pages.get(sessionScope.pagenumber)}"
               class="pagination next round">›</a>
        </c:if>
        <c:if test="${sessionScope.pagenumber == sessionScope.pages.size()}">
            <a style="pointer-events: none;cursor: default; text-decoration: none;"
               href="${pageContext.request.contextPath}${sessionScope.pages.get(sessionScope.pagenumber-1)}"
               class="pagination next round">›</a>
        </c:if>
    </c:if>
</div>
