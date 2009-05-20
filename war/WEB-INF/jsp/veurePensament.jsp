<%@ include file="include.jsp" %>
<%@ include file="menu.jsp" %>

<script type="text/javascript">

Aqui vull posar que quan cliqui a Moderar aparegui l'opció de triar estat positiu o negatiu radio button i un comentari text area

Tambe, quan apreti a modificar que aparegui la desc dins un textarea editable i un boto d'acceptar

Esborrar que pregunti si vol o no i fer-ho.
</script>


    <div id="contingut">
 <h2>Veure Pensament</h2>
 
 <div id="pensament">
 
 <c:choose>
 <c:when test="${empty pensament}"><c:out value="Aquest pensament no existeix."></c:out>
 </c:when>
 <c:otherwise>
 
 <b>${pensament.titol}</b>
 <br/>
 <div id="desc">
 ${pensament.descripcio}
 </div>
 <fmt:formatDate value="${pensament.dataCreacio}" pattern="dd/MM/yyyy hh:mm" />
 
 ${pensament.dataPublicacio}
 ${pensament.dataModificacio}
 ${pensament.autor.nomUsuari }
 
 
 </c:otherwise>
 </c:choose>
 
  </div> <!-- fi pensament -->
 
 <div id="formulari">
 
 <c:choose>
 <c:when test="${ usuariActiu.grup =='MODERADOR'}">
 <a href="moderarPensament.do?id=${pensament.id}">Moderar</a>
 </c:when>
 <c:when test="${ usuariActiu.grup =='REGISTRAT' and usuariActiu.id==pensament.autor.id}">
 <a href="modificarPensament.do?id=${pensament.id}">Modificar</a>
 <br/>
 <a href="esborrarPensament.do?id=${pensament.id}">Esborrar</a>
   </c:when>
 <c:otherwise>
 <br/>
 <a href="llistaPensaments.do?id=${pensament.autor.id }">Veure altres pensaments d'aquest usuari</a>
 </c:otherwise>
 </c:choose>
 
 
 </div> <!-- fi formulari  -->
 
    </div> <!-- fi contingut -->

    
<%@ include file="footer.jsp" %>
        
  