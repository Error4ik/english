package com.voronin.auth.config;

import javax.servlet.ServletException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Filter for utf-8 encoding.
 *
 * @author Alexey Voronin.
 * @since 07.05.2018.
 */
@WebFilter(urlPatterns = {"/*"})
public class CharacterEncodingFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Sets the utf-8 encoding in the request and response.
     *
     * @param request  request.
     * @param response response.
     * @param chain    filter chain.
     * @throws IOException      exception.
     * @throws ServletException exception.
     */
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
