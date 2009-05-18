package gpp.web;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import gpp.bean.Pensament;
import gpp.bean.Usuari;
import gpp.bean.UsuariGrup;
import gpp.servei.PensamentServei;
import gpp.servei.UsuariServei;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




import javax.annotation.Resource;

@Controller
public class LlistaPensamentsController {
    
	@Resource
    private PensamentServei pServei;
   
	@Resource
	private UsuariServei uServei;
	
	private String llistaPensaments = "llistaPensaments";
	
    @RequestMapping(method = RequestMethod.GET, value="/llistaPensaments.do")
    public String llistaPensaments(HttpServletRequest request, Model model) {
        
    	HttpSession sessio = request.getSession();
    	Usuari usuariActiu;
    	List <Pensament> pensaments;
    	String llistaVots = "vots";  
    	
    	usuariActiu = (Usuari) sessio.getAttribute("usuari");
    	
    	int usuariId = ServletRequestUtils.getIntParameter(request, "id", -1);
  
    	Usuari autor = null;
    	
    	if(usuariId!=-1) autor = uServei.getUsuariRegistrat(usuariId);
	     
    	
    	if(usuariActiu!=null){
    		if(usuariActiu.getGrup()==UsuariGrup.MODERADOR){
    			if(usuariId!=-1) pensaments = pServei.getPensamentsAModerarPerUsuariId(usuariId);
            	else pensaments = pServei.getPensamentsAModerar();
        		   
    		}else{
    			llistaVots ="votsUR";
        		if(usuariId!=-1) pensaments = pServei.getPensamentsPopularitatPerUsuariId(usuariId);
        		else pensaments = pServei.getPensamentsPopularitat(); 

    		}
    		
    	}else{
    		if(usuariId!=-1) pensaments = pServei.getPensamentsPopularitatPerUsuariId(usuariId);
    		else pensaments = pServei.getPensamentsPopularitat(); 
    	}
        
    	String pensamentsVotats = (String) sessio.getAttribute(llistaVots);
    	
    	model.addAttribute("llistaVots", pensamentsVotats);
    	model.addAttribute("usuari", autor);
    	model.addAttribute("usuariActiu", usuariActiu);
    	model.addAttribute("pensaments", pensaments);

    	return llistaPensaments;
    }
    
    
   
}
