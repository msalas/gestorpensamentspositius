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
public class VeurePerfilController {

	@Resource
	UsuariServei uServ;
	
	Usuari usuariActiu;
	
	private String viewName = "veurePerfil";
	
	 @RequestMapping(method = RequestMethod.GET, value="/veurePerfil.do")
	    public String veurePerfil(HttpServletRequest request, Model model) {
		 
		 int id = ServletRequestUtils.getIntParameter(request, "id", -1);
		 	HttpSession sessio = request.getSession();
	    	usuariActiu = (Usuari) sessio.getAttribute("usuari");
	    
	    Usuari u = uServ.getUsuariRegistrat(id);
		    model.addAttribute("usuariPerfil",u);
	    	model.addAttribute("usuariActiu", usuariActiu);
		    
		 return viewName;
	 }
	 
}
