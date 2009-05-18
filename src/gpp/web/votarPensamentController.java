package gpp.web;

import gpp.bean.Pensament;
import gpp.bean.Usuari;
import gpp.bean.UsuariGrup;
import gpp.servei.PensamentServei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class votarPensamentController {

	@Resource
	private PensamentServei pServ;
	
	
    @ModelAttribute("command")
    public Pensament getCommand(@RequestParam(value = "id", required = true)
    Integer id, HttpServletRequest request) {
        Pensament p = new Pensament();
        
        try {
            if (id != null) {
                p = pServ.getPensament(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET,value="/votarPensament.do")
    public String processSubmit(@ModelAttribute("command")
    Pensament p, BindingResult result, HttpServletRequest request,  HttpServletResponse response,Model model) {
	    
		    HttpSession session = request.getSession(true);
	        Usuari usuari = (Usuari) session.getAttribute("usuari");
	        String pensamentsVotats = null;
	        String llistaVots = "vots";
	        if(usuari!=null && usuari.getGrup()==UsuariGrup.MODERADOR){
	        	//No fem res	
	        }else{
	        	if(usuari!=null && usuari.getGrup()==UsuariGrup.REGISTRAT) llistaVots ="votsUR";
	        	pensamentsVotats = (String) session.getAttribute(llistaVots);
		        if(pensamentsVotats==null) pensamentsVotats="";
	        	if(!pensamentsVotats.contains("V"+p.getId())){
		        	pensamentsVotats+="V"+p.getId();
		        	pServ.votarPensament(usuari, p);
		        	session.setAttribute(llistaVots, pensamentsVotats);
		        }
	        }
	        
		    return "redirect:llistaPensaments.do";
	        }

}
