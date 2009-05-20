package gpp.web;

import gpp.bean.Comentari;
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
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ModerarPensamentController {

	@Resource
	PensamentServei pServ;
	
	@Resource
	PensamentValidator validator;
	
	Usuari usuariActiu;
	private String viewName ="veurePensament";
	
	
	 @RequestMapping(method = RequestMethod.POST, value="/moderarPensament.do")
	    public String onSubmit(@ModelAttribute("command")
	    	    ModerarPensamentCommand cmd,  BindingResult errors,Model model,HttpServletRequest request) {
		
		 	HttpSession sessio = request.getSession();
	    	usuariActiu = (Usuari) sessio.getAttribute("usuari");
	    	
	    	Pensament p = new Pensament();
	        p.setId(cmd.getId());
	        Comentari c = new Comentari();
	        c.setDescripcio(cmd.getComentari());
	        
	        c.setId(cmd.getComId());
	        p.setComentari(c);
	        
	        p.setEstat(PensamentEstat.valueOf(cmd.getEstat()));
	    	if(usuariActiu!=null && usuariActiu.getGrup()==UsuariGrup.MODERADOR){ 
	    	
	    		
	    		p.getComentari().setAutor(usuariActiu);
		    validator.validarPensamentModerat(p, errors);
		 
		    if (errors.hasErrors()) {
		    	return viewName;
		    }
		 
		 	pServ.moderarPensament(p);
	    	}
	    	
		 return "redirect:llistaPensaments.do";
	 }
	 
}
