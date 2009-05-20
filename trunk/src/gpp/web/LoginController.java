package gpp.web;

import gpp.bean.Usuari;
import gpp.servei.UsuariServei;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	@Resource
    private UsuariServei uServei;
	private String viewName = "redirect:llistaPensaments.do";
	
	@RequestMapping(method = RequestMethod.POST, value="/login.do")
    public String login(HttpServletRequest request, Model model) {
        
    	String username = ServletRequestUtils.getStringParameter(request,"nom","");
    	String password = ServletRequestUtils.getStringParameter(request,"password","");
    	
    //Encriptacio de password seria ideal
    	Usuari usuari = uServei.login(username, password);
    	
    	HttpSession session;
    	   	if(usuari!=null){
    	   		session= request.getSession(true);
    	   		session.setAttribute("usuari", usuari);
    	   		// Recuperar vots realitzats de l'usuari
    	   		
    	   		
    	   		//Fem que se'n recordi de l'usuari per temps indefinit
    	   		session.setMaxInactiveInterval(-1);
    	   		viewName+="?id="+usuari.getId();
    	   	}
    	
    	
    	return viewName;
    }
    
}
