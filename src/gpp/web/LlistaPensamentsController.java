package gpp.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import gpp.bean.Pensament;
import gpp.bean.Usuari;
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
	
	private String viewName = "llistaPensaments";
  
    @RequestMapping(method = RequestMethod.GET, value="/llistaPensaments.do")
    public String handle(HttpServletRequest request, Model model) {
        
    	
    	List <Pensament> pensaments;
    	int perfil = 0;
    	int usuariId = ServletRequestUtils.getIntParameter(request, "id", -1);
  
    	Usuari autor = null;
    	
    	if(usuariId!=-1) autor = uServei.getUsuariRegistrat(usuariId);
    	
    	//Si és Moderador mostrem pensaments  a moderar
    	if(perfil==2){
    		if(usuariId!=-1) pensaments = pServei.getPensamentsAModerarPerUsuariId(usuariId);
        	else pensaments = pServei.getPensamentsAModerar();
    		   
    	}else{
    		if(usuariId!=-1) pensaments = pServei.getPensamentsPopularitatPerUsuariId(usuariId);
    		else pensaments = pServei.getPensamentsPopularitat(); 
    	}
          	
    	model.addAttribute("usuari", autor);
    	model.addAttribute("perfil", perfil);
    	model.addAttribute("pensaments", pensaments);

    	return viewName;
    }
    
    
    /*
    @RequestMapping(method = RequestMethod.POST, value="/report/removeMyReport.do")
    public ModelAndView removeReport(HttpServletRequest request, Model model) {
        int reportId = ServletRequestUtils.getIntParameter(request, "reportId", -1);
        Log.debug(Log.GENERAL,"MyReportsController::removeReport() - reportId = " + reportId);
        reportService.deleteReport(reportId);
        Log.debug(Log.GENERAL,"MyReportsController::removeReport() - deleted reportId = " + reportId);
        return new ModelAndView(viewName, "ambassadorId", SessionManager.getCurrentEndUser(request).getAmbassador().getId()); 
    }
    
    @RequestMapping(method = RequestMethod.GET, value="/report/mobileDisplayMyReports.do")
    public ModelAndView handleMobile(HttpServletRequest request, Model model) {
        int ambassadorId = SessionManager.getCurrentEndUser(request)
                .getAmbassador().getId();
        
        PAFUtil.getInstance()
            .processRequest(request, "My ambassador reports", model.asMap(), "");
        
        return new ModelAndView(mobileViewName, "ambassadorId", ambassadorId);
    }*/
}
