package com.example.asm.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailerService {
    @Autowired
    JavaMailSender sender;

    @Async // Chạy ngầm để người dùng không phải đợi
    public void sendShareEmail(String toEmail, String productId, String productName) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            helper.setFrom("J5 Shop <tuantruong.demo@gmail.com>");
            helper.setTo(toEmail);
            helper.setSubject("Bạn có một gợi ý sản phẩm từ J5 Shop!");

            // Nội dung Email (HTML)
            String url = "http://localhost:8080/product/detail/" + productId;
            String body = "<h3>Xin chào!</h3>" +
                    "<p>Có người muốn chia sẻ sản phẩm này với bạn:</p>" +
                    "<h4 style='color: #BC6C25;'>" + productName + "</h4>" +
                    "<p><a href='" + url + "'>Xem chi tiết sản phẩm tại đây</a></p>" +
                    "<br><hr>" +
                    "<small>J5 Shop - Thời trang & Công nghệ đẳng cấp.</small>";

            helper.setText(body, true);
            sender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}