package gpp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutController {

	
	@RequestMapping(method = RequestMethod.POST, value="/logout.do")
    public String logout(HttpServletRequest request, Model model) {
        
    	HttpSession session = request.getSession();
    	session.invalidate();
    	
    	
    	return "redirect:llistaPensaments.do";
    }
	

	@RequestMapping(method = RequestMethod.GET, value="/logout.do")
    public String logout2(HttpServletRequest request, Model model) {
        
    	HttpSession session = request.getSession();
    	session.invalidate();
    	
    	
    	return "redirect:llistaPensaments.do";
    }
}
