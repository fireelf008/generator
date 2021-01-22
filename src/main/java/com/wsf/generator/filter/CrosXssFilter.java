package com.wsf.generator.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter
@Slf4j
public class CrosXssFilter implements Filter {

    private static List<String> excludeList = new ArrayList<>();
    static {
        excludeList.add("/ref/duties/edit");
        excludeList.add("/my/info/edit");
        excludeList.add("/my/info/update");
        excludeList.add("/my/info/save");
        excludeList.add("/all/info/toDetail");

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (excludeList.contains(httpServletRequest.getRequestURI())) {
            chain.doFilter(request, response);
        } else {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            //跨域设置
            if (response instanceof HttpServletResponse) {
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                //通过在响应 header 中设置 ‘*’ 来允许来自所有域的跨域请求访问。
                httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
                //通过对 Credentials 参数的设置，就可以保持跨域 Ajax 时的 Cookie
                //设置了Allow-Credentials，Allow-Origin就不能为*,需要指明具体的url域
                //httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
                //请求方式
                httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
                //（预检请求）的返回结果（即 Access-Control-Allow-Methods 和Access-Control-Allow-Headers 提供的信息） 可以被缓存多久
                httpServletResponse.setHeader("Access-Control-Max-Age", "86400");
                //首部字段用于预检请求的响应。其指明了实际请求中允许携带的首部字段
                httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
            }
            //sql,xss过滤
            log.info("CrosXssFilter.......orignal url:{},ParameterMap:{}", httpServletRequest.getRequestURI(), JSONObject.toJSONString(httpServletRequest.getParameterMap()));
            XssHttpServletRequestWrapper xssHttpServletRequestWrapper = new XssHttpServletRequestWrapper(httpServletRequest);
            chain.doFilter(xssHttpServletRequestWrapper, response);
            log.info("CrosXssFilter..........doFilter url:{},ParameterMap:{}", xssHttpServletRequestWrapper.getRequestURI(), JSONObject.toJSONString(xssHttpServletRequestWrapper.getParameterMap()));
        }
    }

    @Override
    public void destroy() {

    }
}