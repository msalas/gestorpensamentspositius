<%@ include file="include.jsp" %>
<%@ include file="menu.jsp" %>

    <div id="contingut">
 <h2>Crear Pensament</h2>
 
 <div id="formulari">
 <form method="post" name="crearPensament" action="crearPensament.do">
<label>T�tol</label><input type="text"  value="" name="titol" id="titol"/>
<label>Descripci�</label><input type="textarea"  value="" name="desc" id="desc" rows="10" cols="60" />
<input type="submit" id="submit" name="Submit"/>
</form>
 
  </div> <!-- fi formulari -->
 
 
    </div> <!-- fi contingut -->

    
<%@ include file="footer.jsp" %>
        
  