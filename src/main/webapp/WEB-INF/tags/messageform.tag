<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="${Constants.MESSAGES_BUNDLE}"/>
<%@ attribute name="actionStatus" required="true" type="java.lang.Boolean" %>
<%@ attribute name="actionMessage" required="false" type="java.lang.String" %>
<%@ attribute name="actionCaption" required="true" type="java.lang.String" %>

<style>
    body {
        font-family: Arial, Helvetica, sans-serif;
    }

    /* The Modal (background) */
    .modal {
        /*display: none; !* Hidden by default *!*/
        display: block; /* Hidden by default */
        position: fixed; /* Stay in place */
        z-index: 1; /* Sit on top */
        padding-top: 100px; /* Location of the box */
        left: 0;
        top: 0;
        width: 100%; /* Full width */
        height: 100%; /* Full height */
        overflow: auto; /* Enable scroll if needed */
        background-color: rgb(0, 0, 0); /* Fallback color */
        background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
    }

    /* Modal Content */
    .modal-content {
        position: relative;
        background-color: #fefefe;
        margin: auto;
        padding: 0;
        border: 1px solid #888;
        width: 30%;
        box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
        -webkit-animation-name: animatetop;
        -webkit-animation-duration: 0.4s;
        animation-name: animatetop;
        animation-duration: 0.4s
    }

    /* Add Animation */
    @-webkit-keyframes animatetop {
        from {
            top: -300px;
            opacity: 0
        }
        to {
            top: 0;
            opacity: 1
        }
    }

    @keyframes animatetop {
        from {
            top: -300px;
            opacity: 0
        }
        to {
            top: 0;
            opacity: 1
        }
    }

    /* The Close Button */
    .close {
        color: white;
        float: right;
        font-size: 28px;
        font-weight: bold;
    }

    .close:hover,
    .close:focus {
        color: #000;
        text-decoration: none;
        cursor: pointer;
    }

    .modal-header.ok {
        padding: 2px 16px;
        background-color: #5cb85c;
        color: white;
    }

    .modal-header.bad {
        padding: 2px 16px;
        background-color: red;
        color: white;
    }

    .modal-body {
        padding: 2px 16px;
    }

    .modal-footer.ok {
        padding: 2px 16px;
        background-color: #5cb85c;
        color: white;
    }

    .modal-footer.bad {
        padding: 2px 16px;
        background-color: red;
        color: white;
    }
</style>

<body>
<div id="myModal" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
        <c:choose>
            <c:when test="${actionStatus}">
                <div class="modal-header ok">
                    <h3><c:out value="${actionCaption}"/></h3>
                    <span class="close">&times;</span>
                        <%--                    <h3><fmt:message key="entity.dao.operation"/>&nbsp;<fmt:message key="entity.action.result.ok"/></h3>--%>
                </div>
            </c:when>
            <c:otherwise>
                <div class="modal-header bad">
                    <h3><c:out value="${actionCaption}"/></h3>
                    <span class="close">&times;</span>
                        <%--                    <h3><fmt:message key="entity.action.create"/>&nbsp;<fmt:message key="entity.action.result.bad"/></h3>--%>
                </div>
            </c:otherwise>
        </c:choose>
        <div class="modal-body">
            <p><c:out value="${actionMessage}"/></p>
        </div>
        <c:choose>
            <c:when test="${actionStatus}">
                <div class="modal-footer ok">
                        <%--                    <h4><c:out value="${actionCaption}"/></h4>--%>
                        <%--                    <span class="close">&times;</span>--%>
                        <%--                    <h4><fmt:message key="entity.dao.operation"/>&nbsp;<fmt:message key="entity.action.result.ok"/></h4>--%>
                </div>
            </c:when>
            <c:otherwise>
                <div class="modal-footer bad">
                        <%--                    <h4><c:out value="${actionCaption}"/></h4>--%>
                        <%--                    <span class="close">&times;</span>--%>
                        <%--                    <h4><fmt:message key="entity.action.create"/>&nbsp;<fmt:message key="entity.action.result.bad"/></h4>--%>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</div>
<script>
    // // Get the modal
    var modal = document.getElementById("myModal");

    // Get the button that opens the modal
    var btn = document.getElementById("myBtn");

    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];

    // When the user clicks on <span> (x), close the modal
    span.onclick = function () {
        modal.style.display = "none";
    }
    //
    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
</script>