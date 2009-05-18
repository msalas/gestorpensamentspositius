package gpp.web;

import gpp.servei.PensamentServei;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MarcarPensamentController {
	
	@Resource
    private PensamentServei pServei;
	
	@RequestMapping(method = RequestMethod.GET, value="/marcarPensament.do")
    public String marcarPensament(HttpServletRequest request, Model model) {
        
    	int pensamentId = ServletRequestUtils.getIntParameter(request, "id", -1);
    	  
    	if(pensamentId!=-1)	pServei.marcarPensament(pensamentId);
    	
    	return "redirect:llistaPensaments.do";
    }
    
}
