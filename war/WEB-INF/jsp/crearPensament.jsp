<%@ include file="include.jsp" %>
<%@ include file="menu.jsp" %>

    <div id="contingut">
 <h2>Crear Pensament</h2>
 
 <div id="formulari">
 <form method="post" name="crearPensament" action="crearPensament.do">
<label><b>Títol</b></label><input type="text"  value="" name="titol" id="titol"/>
<br/><br/><label><b>Descripció</b></label><input type="textarea"  value="" name="desc" id="textarea"  /><br/><br/>
<input type="submit" id="submit" name="Crear" value="Crear" />
</form>
 
  </div> <!-- fi formulari -->
 
 
    </div> <!-- fi contingut -->

    
<%@ include file="footer.jsp" %>
        
  