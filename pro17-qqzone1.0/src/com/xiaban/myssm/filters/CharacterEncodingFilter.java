package com.xiaban.myssm.filters;

import com.xiaban.util.StringUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("*.do")
public class CharacterEncodingFilter implements Filter {

    private String encoding = "UTF-8";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encodingStr = filterConfig.getInitParameter("encoding");
        if(StringUtil.isNotEmpty(encodingStr)){
            encoding = encodingStr;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ((HttpServletRequest)servletRequest).setCharacterEncoding(encoding);
        System.out.println("设置编码成功");
        filterChain.doFilter(servletRequest,servletResponse);
        
    }

    @Override
    public void destroy() {

    }
}
