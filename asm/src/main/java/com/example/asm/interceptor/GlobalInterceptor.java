package com.example.asm.interceptor;

import com.example.asm.dao.CategoryDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class GlobalInterceptor implements HandlerInterceptor {
    @Autowired
    CategoryDAO dao;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Chỉ bơm dữ liệu khi request trả về view hợp lệ (không phải REST API hay Redirect)
        if(request.getAttribute("cates") == null && modelAndView != null && !modelAndView.isEmpty()) {
            request.setAttribute("cates", dao.findAll());
        }
    }
}