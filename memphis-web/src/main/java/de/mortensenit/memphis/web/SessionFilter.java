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
 * Session Bean implementation class SesionFilter
 */
@WebFilter("/pages/*")
public class SessionFilter implements Filter {

	Logger logger = Logger.getLogger(getClass().getSimpleName());

	/**
	 * Default constructor.
	 */
	public SessionFilter() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	/**
	 * prevent back button after logout
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpSession session = httpServletRequest.getSession(false);

		httpServletResponse.setHeader("Cache-Control",
				"no-cache, no-store, must-revalidate");
		httpServletResponse.setHeader("Pragma", "no-cache");
		httpServletResponse.setDateHeader("Expires", 0);
		
		String url = httpServletRequest.getRequestURI();
		logger.info("URL: " + url);
		if (url.equals("/memphis-web/web/index.xhtml")
				|| url.equals("/memphis-web/")) {
			logger.info("Index page, go on...");
			chain.doFilter(request, response);
		} else if (url.startsWith("/memphis-web/web/pages/")) {
			if (session == null || session.getAttribute("user") == null) {
				logger.info("No user logged in, redirect to index...");
				httpServletResponse.sendRedirect(httpServletRequest
						.getContextPath() + "/index.xhtml");
			} else {
				logger.info("Go on...");
				chain.doFilter(request, response);
			}
		}
	}

}
