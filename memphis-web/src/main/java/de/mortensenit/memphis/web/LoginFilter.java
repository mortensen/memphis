package de.mortensenit.memphis.web;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Checks if the user is logged in and redirects to index.xhtml if not.
 * 
 * @author frederik.mortensen
 * 
 */
@WebFilter("/web/pages/*")
public class LoginFilter implements Filter {

	private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Override
	public void init(FilterConfig config) throws ServletException {
		// If you have any <init-param> in web.xml, then you could get them
		// here by config.getInitParameter("name") and assign it as field.
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("user") == null) {
			logger.info("LoginFilter hat keinen User gefunden...");
			// no user, get out
			response.sendRedirect("/memphis-web/");
		} else {
			// continue with user
			logger.info("LoginFilter hat den eingeloggten User gefunden...");
			chain.doFilter(servletRequest, servletResponse);
		}
	}

	@Override
	public void destroy() {
		// If you have assigned any expensive resources as field of
		// this Filter class, then you could clean/close them here.
	}

}