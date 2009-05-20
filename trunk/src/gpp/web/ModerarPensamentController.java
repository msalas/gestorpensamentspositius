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
	private String viewName ="moderarPensament";
	
	@RequestMapping(method = RequestMethod.GET, value="/moderarPensament.do")
    public String moderarPensament(HttpServletRequest request, Model model) {
        
		HttpSession sessio = request.getSession();
    	usuariActiu = (Usuari) sessio.getAttribute("usuari");
    	
    	if(usuariActiu!=null && usuariActiu.getGrup()==UsuariGrup.MODERADOR){ 
    		int pensamentId = ServletRequestUtils.getIntParameter(request, "id", -1);
    	  
    		Pensament p = pServ.getPensament(pensamentId);
    		    	
    		model.addAttribute("pensament", p);
    	
    	}else viewName="redirect:llistaPensaments.do"; // sino no pot moderar pensaments
    	
    	return viewName;
    }
	
	 @RequestMapping(method = RequestMethod.POST, value="/moderarPensament.do")
	    public String onSubmit(@ModelAttribute("command")
	    	    Pensament p, Model model, BindingResult errors,HttpServletRequest request) {
			
		    validator.validarPensamentModerat(p, errors);
		 
		 if (errors.hasErrors()) {
	            return viewName;
	     }
		 
		 pServ.moderarPensament(p);
		 
		 return "redirect:llistaPensaments.do";
	 }
	 
	 @ModelAttribute("command")
	    public Pensament getCommand(@RequestParam(value = "id", required = true) Integer id,@RequestParam(value = "comId", required = true) Integer comId,@RequestParam(value = "comentari", required = true) String comentari,@RequestParam(value = "estat", required = true) Integer estat, HttpServletRequest request) {
	        
		 	Pensament p = new Pensament();
	        p.setId(id);
	        Comentari c = new Comentari();
	        c.setAutor(usuariActiu);
	        c.setDescripcio(comentari);
	        c.setId(comId);
	        p.setComentari(c);
	        p.setEstat(PensamentEstat.valueOf(estat));
	        return p;
	    }
}
