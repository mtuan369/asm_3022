package com.example.asm.interceptor;

import com.example.asm.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

@Service
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        Account user = (Account) session.getAttribute("user"); // Lấy user từ session

        // 1. Chưa đăng nhập -> Bắt đăng nhập
        if (user == null) {
            session.setAttribute("security-uri", uri);
            response.sendRedirect("/auth/login");
            return false;
        }

        // 2. Đã đăng nhập nhưng cố truy cập trang Admin mà không phải Admin -> Chặn
        // Chỉ admin (admin = true) mới được vào đường dẫn bắt đầu bằng /admin/
        if (uri.startsWith("/admin") && !user.isAdmin()) {
            response.sendRedirect("/auth/access/denied"); // Chuyển trang báo lỗi
            return false;
        }

        return true;
    }
}