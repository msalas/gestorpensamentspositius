package gpp.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import gpp.bean.Pensament;
import gpp.servei.PensamentServei;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
public class LlistaPensamentsController {
    
	@Resource
    private PensamentServei pServei;
    private String viewName = "llistaPensaments";
    private String viewNameMod = "/mod/llistaPensaments";
 
    @RequestMapping(method = RequestMethod.GET, value="/llistaPensaments.do")
    public ModelAndView handle(HttpServletRequest request, Model model) {
        
    	List <Pensament> pensaments = pServei.getPensamentsPopularitat();
    	
        return new ModelAndView(viewName, "pensaments", pensaments);
    }
    
    
    @RequestMapping(method = RequestMethod.GET, value="/mod/llistaPensaments.do")
    public ModelAndView handle2(HttpServletRequest request, Model model) {
        
    	List <Pensament> pensaments = pServei.getPensamentsAModerar();
    	
        return new ModelAndView(viewNameMod, "pensaments", pensaments);
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
