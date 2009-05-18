<c:choose>
<c:when test="${usuariActiu.grup=='REGISTRAT'}">
<%@ include file="headerSigned.jsp" %>
<%@ include file="menuUR.jsp" %>

</c:when>
<c:when test="${usuariActiu.grup=='MODERADOR'}">
<%@ include file="headerSigned.jsp" %>
<%@ include file="menuM.jsp" %>

</c:when>
<c:otherwise>
<%@ include file="header.jsp" %>
<%@ include file="menuBASIC.jsp" %>
</c:otherwise>
</c:choose>