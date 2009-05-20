package gpp.web;



import gpp.bean.Pensament;
import gpp.bean.PensamentEstat;
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
public class CrearPensamentController {

	@Resource
	PensamentServei pServ;
	
	@Resource
	PensamentValidator validator;
	
	Usuari usuariActiu;
	private String viewName ="crearPensament";
	
	 @RequestMapping(method = RequestMethod.GET, value="/crearPensament.do")
	    public String crearPensament(HttpServletRequest request, Model model) {
		 
		 	HttpSession sessio = request.getSession();
	    	usuariActiu = (Usuari) sessio.getAttribute("usuari");
	    	
	    	UsuariGrup u = usuariActiu.getGrup();
	    	boolean b = usuariActiu.getGrup()==UsuariGrup.MODERADOR;
	    
	    	b = (u ==UsuariGrup.MODERADOR);
		    if(usuariActiu==null || usuariActiu.getGrup()==UsuariGrup.MODERADOR) viewName="redirect:llistaPensaments.do";
		 
		    model.addAttribute("usuariActiu", usuariActiu);
		 return viewName;
	 }
	 
	 @RequestMapping(method = RequestMethod.POST, value="/crearPensament.do")
	    public String onSubmit(@ModelAttribute("command")
	    	    Pensament p, BindingResult errors,HttpServletRequest request) {
		
		 validator.validarPensament(p, errors);
		 if (errors.hasErrors()) {
	            return viewName;
	     }
		 pServ.crearPensament(p);
		 
		 return "redirect:llistaPensaments.do?id="+usuariActiu.getId();
	 }
	 
	 @ModelAttribute("command")
	    public Pensament getCommand(@RequestParam(value = "titol", required = false) String titol,@RequestParam(value = "desc", required = false) String desc, HttpServletRequest request) {
	        
		 	Pensament p = new Pensament();
	        p.setTitol(titol);
	        p.setDescripcio(desc);
	        p.setAutor(usuariActiu);
	        p.setEstat(PensamentEstat.POSITIU);
	        
	        return p;
	    }
}
