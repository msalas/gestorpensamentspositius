<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML Basic 1.0//EN" "http://www.w3.org/TR/xhtml-basic/xhtml-basic10.dtd">

<%@ include file="include.jsp" %>
<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>
    


    <div id="contingut">
 <h2>Llista pensaments</h2>
    <p>Greetings, it is now <c:out value="${now}"/></p>
    <c:forEach items="${pensaments}" var="item">
     <p><c:out value="${item.titol}"/></p>
 
    </c:forEach>
 
    </div>

    
<%@ include file="footer.jsp" %>
        
  