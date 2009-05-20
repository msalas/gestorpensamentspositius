package gpp.web;

import gpp.bean.Pensament;
import gpp.bean.Usuari;
import gpp.bean.UsuariGrup;
import gpp.servei.PensamentServei;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EsborrarPensamentController {

	@Resource
	PensamentServei pServ;
	
	@Resource
	PensamentValidator validator;
	
	Usuari usuariActiu;
	private String viewName ="veurePensament";
	

	 @RequestMapping(method = RequestMethod.POST, value="/esborrarPensament.do")
	    public String onSubmit(@ModelAttribute("command")
	    	    Pensament p, BindingResult errors,Model model, HttpServletRequest request) {
		
			HttpSession sessio = request.getSession();
	    	usuariActiu = (Usuari) sessio.getAttribute("usuari");
	    	
	    	if(usuariActiu==null || usuariActiu.getGrup()==UsuariGrup.MODERADOR) viewName="redirect:llistaPensaments.do";
		 
		    model.addAttribute("usuariActiu", usuariActiu);
		 
		    //Comprovar que existeix o que l'usuari es el seu creador
		    validator.validarPensament(p, errors);
		 
		 if (errors.hasErrors()) {
	            return viewName;
	     }
		 pServ.esborrarPensament(p);
		 
		 return "redirect:llistaPensaments.do?id="+usuariActiu.getId();
	 }
	 
	 @ModelAttribute("command")
	    public Pensament getCommand(@RequestParam(value = "id", required = true) Integer id, HttpServletRequest request) {
	        
		 	Pensament p = new Pensament();

	        p.setId(id);
	        
	        return p;
	    }
}
