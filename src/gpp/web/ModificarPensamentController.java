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
import org.springframework.web.util.HtmlUtils;

@Controller
public class ModificarPensamentController {

	@Resource
	PensamentServei pServ;
	
	@Resource
	PensamentValidator validator;
	
	Usuari usuariActiu;
	private String viewName ="veurePensament";
	

	 @RequestMapping(method = RequestMethod.POST, value="/modificarPensament.do")
	    public String onSubmit(@ModelAttribute("command")
	    	    Pensament p, BindingResult errors,Model model, HttpServletRequest request) {
		
			HttpSession sessio = request.getSession();
	    	usuariActiu = (Usuari) sessio.getAttribute("usuari");
	    	
	    	if(usuariActiu==null || usuariActiu.getGrup()==UsuariGrup.MODERADOR) viewName="redirect:llistaPensaments.do";
		 
		    model.addAttribute("usuariActiu", usuariActiu);
		 
		    validator.validarPensament(p, errors);
		 
		 if (errors.hasErrors()) {
	            return viewName;
	     }
		 pServ.modificarPensament(p);
		 
		 return "redirect:llistaPensaments.do?id="+usuariActiu.getId();
	 }
	 
	 @ModelAttribute("command")
	    public Pensament getCommand(@RequestParam(value = "id", required = true) Integer id,@RequestParam(value = "titol", required = false) String titol,@RequestParam(value = "desc", required = false) String desc, HttpServletRequest request) {
	        
		 	Pensament p = new Pensament();
	        p.setTitol(HtmlUtils.htmlEscape(titol));
	        p.setDescripcio(HtmlUtils.htmlEscape(desc));
	        p.setId(id);
	        
	        return p;
	    }
}
