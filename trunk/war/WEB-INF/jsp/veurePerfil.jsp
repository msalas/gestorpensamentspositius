<%@ include file="include.jsp" %>
<%@ include file="menu.jsp" %>

<script type="text/javascript">

Aqui vull posar que quan cliqui a Moderar aparegui l'opció de triar estat positiu o negatiu radio button i un comentari text area

Tambe, quan apreti a modificar que aparegui la desc dins un textarea editable i un boto d'acceptar

Esborrar que pregunti si vol o no i fer-ho.
</script>


    <div id="contingut">
 <c:choose>
 <c:when test="${ usuariPerfil.id==usuariActiu.id}">
 <h2>El meu perfil</h2>

 </c:when>
 <c:otherwise>
 <h2>Veure Perfil de <c:out value="${usuariPerfil.nomUsuari}" /></h2>
 </c:otherwise>
 </c:choose>
 
 <div id="usuariPerfil">
 
 <c:choose>
 <c:when test="${empty usuariPerfil}"><c:out value="Aquest usuari no existeix."></c:out>
 </c:when>
 <c:otherwise>
 
 <label><b>${usuariPerfil.nomUsuari}</b></label>
 
 <label>
<c:choose>
 <c:when test="${ usuariPerfil.edat==0}">
 Edat no definida

 </c:when>
 <c:otherwise>
 ${usuariPerfil.edat}
 </c:otherwise>
 </c:choose>
 </label>


 
 
 </c:otherwise>
 </c:choose>
 
  </div> <!-- fi perfil usuari -->
 
 <div id="formulari">
 
 
 <c:choose>
 <c:when test="${ usuariActiu.grup =='MODERADOR'}">
 <a href="modificarUsuari.do?id=${usuariPerfil.id}">Modificar</a>
 <br/>
 <a href="esborrarUsuari.do?id=${usuariPerfil.id}">Esborrar</a>
 </c:when>
 <c:when test="${ usuariActiu.grup =='REGISTRAT' and usuariActiu.id==usuariPerfil.id}">
 <a href="modificarUsuari.do?id=${usuariPerfil.id}">Modificar</a>
   </c:when>
 <c:otherwise>
 <br/>
 <a href="llistaPensaments.do?id=${usuariPerfil.id }">Veure pensaments d'aquest usuari</a>
 </c:otherwise>
 </c:choose>
 
 
 </div> <!-- fi formulari  -->
 
    </div> <!-- fi contingut -->

    
<%@ include file="footer.jsp" %>
        
 