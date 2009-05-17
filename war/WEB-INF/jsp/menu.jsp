<c:choose>
<c:when test="${perfil==1}">
<%@ include file="headerSigned.jsp" %>
<%@ include file="menuUR.jsp" %>

</c:when>
<c:when test="${perfil==2}">
<%@ include file="headerSigned.jsp" %>
<%@ include file="menuM.jsp" %>

</c:when>
<c:otherwise>
<%@ include file="header.jsp" %>
<%@ include file="menuBASIC.jsp" %>
</c:otherwise>
</c:choose>