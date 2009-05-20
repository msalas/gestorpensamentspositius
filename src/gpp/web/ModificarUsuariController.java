package gpp.web;

import gpp.bean.Pensament;
import gpp.bean.Usuari;
import gpp.bean.UsuariGrup;
import gpp.servei.UsuariServei;
import gpp.servei.UsuariServeiImpl;

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
public class ModificarUsuariController {

	@Resource
	UsuariServei uServ;
	
	@Resource
	UsuariValidator validator;
	
	Usuari usuariActiu;
	private String viewName ="veurePerfil";
	

	 @RequestMapping(method = RequestMethod.POST, value="/modificarUsuari.do")
	    public String onSubmit(@ModelAttribute("command")
	    	    Usuari u, BindingResult errors, Model model,HttpServletRequest request) {
		
			HttpSession sessio = request.getSession();
	    	usuariActiu = (Usuari) sessio.getAttribute("usuari");
	    	
	    	if(!((usuariActiu.getId()==u.getId() && usuariActiu.getGrup()==UsuariGrup.REGISTRAT) || usuariActiu.getGrup()==UsuariGrup.MODERADOR)) errors.reject("No tens permisos per modificar aquest usuari.");
	    	
	    	validator.validarUsuari(u, errors);
	    			 
	    	 if (errors.hasErrors()) {
	    	           return viewName;
	    	 }
	    	uServ.modificarUsuari(u);
	    			 
		 
		    model.addAttribute("usuariActiu", usuariActiu);
		 
		 
		 return "redirect:llistaPensaments.do?id="+u.getId();
	 }
	 
	 @ModelAttribute("command")
	    public Usuari getCommand(@RequestParam(value = "id", required = true) Integer id,@RequestParam(value = "contra", required = false) String contra,@RequestParam(value = "email", required = false) String email, HttpServletRequest request) {
	        
		 	Usuari u = new Usuari();
		 	u.setEmail(email);
		 	u.setContrassenya(contra);
	        u.setId(id);
	        
	        return u;
	    }

}
