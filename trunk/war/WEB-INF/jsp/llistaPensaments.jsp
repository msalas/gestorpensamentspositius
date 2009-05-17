<%@ include file="include.jsp" %>
<%@ include file="menu.jsp" %>

    <div id="contingut">
 <h2>Llista pensaments <c:if test="${not empty usuari}">de <c:out value="${usuari.nomUsuari}"></c:out> </c:if>
 </h2>
 
 <div id="llista">
    <c:choose>
    <c:when test="${empty pensaments}">Aquest usuari no t� pensaments.
    </c:when>
    <c:otherwise>
    <table>
    
     <c:forEach items="${pensaments}" var="item">
<tr>
<c:if test="${empty usuari}"><td><a href="veurePerfil?id=${item.autor.id}"><c:out value="${item.autor.nomUsuari}"/></a></td>
</c:if>	
	<td><a href="veurePensament?id=${item.id}"><c:out value="${item.titol}"/></a></td>
	<td><a href="veurePensament?id=${item.id}"><c:out value="${item.descripcio}"/></a></td>
	<td><c:out value="${item.estat}"/></td>
	<td><c:out value="${item.vots}"/> vots</td>
<c:if test="${perfil!=2}">	
	<td><a href="votarPensament.do?id=${item.id}">Votar</a></td>
	<td><a href="marcarPensament.do?id=${item.id}">Marcar</a></td>
</c:if>
</tr>
 
    </c:forEach>
    </table>

    </c:otherwise>
    </c:choose>
   

 </div>
 
 
    </div>

    
<%@ include file="footer.jsp" %>
        
  