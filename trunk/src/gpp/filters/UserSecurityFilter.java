package gpp.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class UserSecurityFilter implements Filter {

    public void init(FilterConfig arg0) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

    	/* HttpServletRequest httpRequest = (HttpServletRequest) request;

        SessionBean sessionBean = SessionManager.getSessionBean(httpRequest, Constant.UG_END_USER);
        if ( sessionBean == null || !sessionBean.isEndUserLogged() ) {
         Log.debug(Log.ADMIN,"SecurityFilter: redirecting to logging page");
         String redirectTo = "/life/mys60/?action=login&returnPage="+httpRequest.getRequestURI()+"?"+(httpRequest.getQueryString()!=null?httpRequest.getQueryString():"");
                ((HttpServletResponse)response).sendRedirect(redirectTo);
                return;

        }
       */ chain.doFilter(request,response);

    }

    public void destroy() {

    }

}
