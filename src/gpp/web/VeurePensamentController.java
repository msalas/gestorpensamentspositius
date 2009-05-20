package gpp.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gpp.bean.Pensament;
import gpp.bean.Usuari;

import gpp.servei.PensamentServei;

@Controller
public class VeurePensamentController {

	@Resource
	PensamentServei pServ;
	Usuari usuariActiu;
	
	private String viewName = "veurePensament";
	
	 @RequestMapping(method = RequestMethod.GET, value="/veurePensament.do")
	    public String veurePensament(HttpServletRequest request, Model model) {
		 
		 int id = ServletRequestUtils.getIntParameter(request, "id", -1);
		 	HttpSession sessio = request.getSession();
	    	usuariActiu = (Usuari) sessio.getAttribute("usuari");
	    
	    	Pensament pensament = pServ.getPensament(id);
		    model.addAttribute("pensament",pensament);
	    	model.addAttribute("usuariActiu", usuariActiu);
		    
		 return viewName;
	 }
	 
	
}
