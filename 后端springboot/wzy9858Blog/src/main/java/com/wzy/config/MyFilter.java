package com.wzy.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化方法执行");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        response.setHeader("Access-Control-Allow-Credentials","true");//当客户端跨域并需要传递cookie时，需要设置Access-Control-Allow-Credentials，并且值为“true”  代表是否向页面暴露cookie
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173"); //当客户端跨域并需要传递cookie时，需要设置Access-Control-Allow-Origin，并且值为不能为“*”，需要具体配置
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEAD");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "token,access-control-allow-origin, authority, content-type, version-info, X-Requested-With");

        // 检查是否是预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            System.out.println("预检请求");
            // 设置响应状态码为200 OK
            response.setStatus(HttpServletResponse.SC_OK);
            // 对于预检请求，不需要继续执行过滤器链
            return;
        }
        // 对于非预检请求，继续执行过滤器链
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("销毁方法");
    }
}
