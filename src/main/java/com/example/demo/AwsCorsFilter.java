//package com.example.demo;
//
//
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import java.io.IOException;
//
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE) // 🚀 CRITICAL: Runs before Spring Security or Spring Routers can throw a 404/403
//public class AwsCorsFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
//            throws IOException, ServletException {
//        
//        HttpServletResponse response = (HttpServletResponse) res;
//        HttpServletRequest request = (HttpServletRequest) req;
//
//        // Force explicit matching to your CloudFront distribution origin
//        response.setHeader("Access-Control-Allow-Origin", "https://d3042ckvga29du.cloudfront.net");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, x-requested-with, *");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//
//        // 🚀 THE FIX: If it is an OPTIONS preflight request from any AWS path, short-circuit with a 200 OK immediately
//        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//            response.setStatus(HttpServletResponse.SC_OK);
//        } else {
//            chain.doFilter(req, res);
//        }
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) {}
//
//    @Override
//    public void destroy() {}
//}
//
