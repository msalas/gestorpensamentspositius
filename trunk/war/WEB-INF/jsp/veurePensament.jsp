<%@ include file="include.jsp" %>
<%@ include file="menu.jsp" %>

<script type="text/javascript">

//Aqui vull posar que quan cliqui a Moderar aparegui l'opció de triar estat positiu o negatiu radio button i un comentari text area

//Tambe, quan apreti a modificar que aparegui la desc dins un textarea editable i un boto d'acceptar

//Esborrar que pregunti si vol o no i fer-ho.

</script>


    <div id="contingut">
 <h2>Veure Pensament</h2>
 
 <div id="pensament">
 
 <c:choose>
 <c:when test="${empty pensament}"><c:out value="Aquest pensament no existeix."></c:out>
 </c:when>
 <c:otherwise>
 
  <br/>
 
 

 <div id="dates">
 
 <span id="username"><b><a href="veurePerfil.do?id=${pensament.autor.id }">${pensament.autor.nomUsuari }</a></b></span>
 
 <br/>Data de creació: <fmt:formatDate value="${pensament.dataCreacio}" pattern="dd/MM/yyyy hh:mm" />
 <br/>Data de publicació: <fmt:formatDate value="${pensament.dataPublicacio}" pattern="dd/MM/yyyy hh:mm" />
 <br/>Data de modificació: <fmt:formatDate value="${pensament.dataModificacio}" pattern="dd/MM/yyyy hh:mm" />
</div>

 
 
 <div id="desc">
<b>${pensament.titol}</b>
  <br/><br/>
 ${pensament.descripcio}
 <br/>
 </div>
 
  <br/>
  <c:if test="${not empty pensament.comentari.descripcio }">
<div id="comentari">
<b>Comentari</b>
<br/>
 <i>${pensament.comentari.descripcio }</i>
 </div> 
  </c:if>
 <br/>
 
 <c:choose>
 <c:when test="${ usuariActiu.grup =='MODERADOR'}">
 
 <c:if test="${pensament.estat=='DUBTOS'}">
 <div id="formulari">
 
 <form action="moderarPensament.do" method="post">
 <input type="hidden" value="${pensament.id}" name="id">
 <input type="hidden" value="${pensament.comentari.id}" name="comId">
<label><b>Comentari</b></label>
 <input type="textarea" value="${pensament.comentari.descripcio}" name="comentari" id="textarea">
 <br/><br/>
 <input type="radio" name="estat" value="3" checked="checked" > NEGATIU
 <input type="radio" name="estat" value="1" > POSITIU
 <br/><br/>
 <input type="submit" value="Moderar"> 
 </form>
 
 </div> <!-- fi formulari  -->
 </c:if>
 
 </c:when>
 <c:when test="${ usuariActiu.grup =='REGISTRAT' and usuariActiu.id==pensament.autor.id}">
 
 <br/>
 <div id="formulari">
 
 <form action="modificarPensament.do" method="post">
 <input type="hidden" value="${pensament.id}" name="id"> 
 <label><b>Títol</b></label><input type="text" value="${pensament.titol}" name="titol" id="titol">
 <br/><br/><label><b>Descripció</b></label><input type="textarea" value="${pensament.descripcio}" name="desc" id="textarea">
 <br/><br/><input type="submit" value="Modificar"> 
 
 </form>
 <br/><br/>
 <form action="esborrarPensament.do" method="post">
 <input type="hidden" value="${pensament.id}" name="id"> 
 <input type="submit" value="Esborrar"> 
 </form>
 
 </div> <!-- fi formulari  -->
 
   </c:when>
 <c:otherwise>

 <a href="llistaPensaments.do?id=${pensament.autor.id }">Veure altres pensaments d'aquest usuari</a>
 </c:otherwise>
 </c:choose>
 
  
 
 </c:otherwise>
 </c:choose>
 
  </div> <!-- fi pensament -->
 
 
    </div> <!-- fi contingut -->

    
<%@ include file="footer.jsp" %>
        
  