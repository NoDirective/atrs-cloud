/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.common.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//FIXME tilesのincludeを使用した際にセッションがうまくセットできない問題への暫定対処
// ( issue : https://github.com/spring-projects/spring-session/issues/571 )
public class SessionEnforcerFilter implements Filter {

    private static final Logger logger = LoggerFactory
            .getLogger(SessionEnforcerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (httpServletRequest.getSession(false) == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("sessionEnforcerFilter.doFilter () - Session is null - forcing its creation");
            }
            httpServletRequest.getSession();
            StringBuilder requestURI = new StringBuilder(httpServletRequest
                    .getRequestURI());
            if (httpServletRequest.getQueryString() != null) {
                requestURI.append("?").append(
                        httpServletRequest.getQueryString());
            }
            if (logger.isDebugEnabled()) {
                logger.debug(
                        "sessionEnforcerFilter.doFilter () - Repeating request [{}]",
                        requestURI);
            }
            httpServletResponse.sendRedirect(requestURI.toString());
        } else {
            chain.doFilter(httpServletRequest, response);
        }
    }

    @Override
    public void destroy() {
    }

}
