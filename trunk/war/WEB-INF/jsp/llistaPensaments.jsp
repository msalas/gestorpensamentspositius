<%@ include file="include.jsp" %>
<%@ include file="menu.jsp" %>

    <div id="contingut">
 <h2>Llista de pensaments <c:if test="${not empty usuari}">de <c:out value="${usuari.nomUsuari}"></c:out> </c:if>
 </h2>
 
 <div id="llista">
    <c:choose>
    <c:when test="${empty pensaments}">No hi ha pensaments disponibles.
    </c:when>
    <c:otherwise>
    <table width="100%">
    
     <c:forEach items="${pensaments}" var="item">
<tr>
<c:if test="${empty usuari}"><td><div id="username" ><a href="veurePerfil.do?id=${item.autor.id}"><b><c:out value="${item.autor.nomUsuari}"/></b></a></div></td>
</c:if>	
	<td><a href="veurePensament.do?id=${item.id}"><b><c:out value="${item.titol}"/></b></a></td>
	<td><a href="veurePensament.do?id=${item.id}"><c:out value="${item.descripcio}"/></a></td>
<c:if test="${(usuariActiu.grup=='REGISTRAT' and usuariActiu.id==usuari.id) or usuariActiu.grup=='MODERADOR'}">	<td>
	<c:choose>
	<c:when test="${item.estat == 'POSITIU'}">
	<span style="color:darkBlue;"> <b><c:out value="${item.estat}"/></b></span>	</c:when>
	<c:when test="${item.estat == 'NEGATIU'}">
	<span style="color:red;"> <b><c:out value="${item.estat}"/></b></span>
	</c:when>
	<c:otherwise>
	<span style="color:yellow;"> <b><c:out value="${item.estat}"/></b></span></c:otherwise>
	</c:choose>
	
</td></c:if>	
	<td><c:out value="${item.vots}"/> vots</td>
<c:if test="${usuariActiu.grup != 'MODERADOR' and usuariActiu.id!= item.autor.id}">	
	
	<c:choose>
	<c:when test="${not empty llistaVots}">
	<c:if test="${not fn:contains(llistaVots,V+item.id)}">
			<td><a href="votarPensament.do?id=${item.id}"><b>Votar</b></a></td>
	</c:if>
	</c:when>
	<c:otherwise>
			<td><a href="votarPensament.do?id=${item.id}"><b>Votar</b></a></td>
	</c:otherwise>
	</c:choose>
	
	<td><a href="marcarPensament.do?id=${item.id}"><b>Marcar</b></a></td>

</c:if>
</tr>
 
    </c:forEach>
    </table>

    </c:otherwise>
    </c:choose>
   
 
 </div> <!-- fi llista -->
 
 
    </div> <!-- fi contingut -->

    
<%@ include file="footer.jsp" %>
        
  