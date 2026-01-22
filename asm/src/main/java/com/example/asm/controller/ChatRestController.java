package com.example.asm.controller;

import com.example.asm.dao.ProductDAO;
import com.example.asm.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
public class ChatRestController {

    @Autowired
    ProductDAO productDAO;

    @GetMapping("/rest/chat")
    public Map<String, Object> chat(@RequestParam("message") String message) {
        Map<String, Object> response = new HashMap<>();
        String msg = message.toLowerCase().trim();
        String botAnswer = "";

        // 1. Xử lý chào hỏi
        if (msg.contains("hi") || msg.contains("hello") || msg.contains("chào") || msg.contains("xin chào")) {
            botAnswer = "Chào bạn! Tôi là trợ lý ảo của J5 Shop. Bạn đang tìm kiếm sản phẩm gì? (Ví dụ: Son, Áo, iPhone...)";
        }
        // 2. Xử lý hỏi giá/ship
        else if (msg.contains("giá") || msg.contains("tiền")) {
            botAnswer = "Giá sản phẩm được niêm yết công khai trên web ạ. Bạn nhập tên sản phẩm mình tìm giúp cho nhé!";
        }
        else if (msg.contains("ship") || msg.contains("giao hàng")) {
            botAnswer = "Bên mình giao hàng toàn quốc, phí ship đồng giá 30k nhé!";
        }
        // 3. TÌM KIẾM SẢN PHẨM TRONG DB
        else {
            List<Product> products = productDAO.findByNameContaining(msg);
            if (!products.isEmpty()) {
                int count = products.size();
                botAnswer = "Mình tìm thấy " + count + " sản phẩm phù hợp với từ khóa '" + message + "':<br>";

                // Ghép HTML danh sách sản phẩm để trả về (lấy tối đa 3 món cho đỡ dài)
                for (int i = 0; i < Math.min(3, count); i++) {
                    Product p = products.get(i);
                    // Format tiền tệ
                    String price = NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(p.getPrice());

                    botAnswer += String.format(
                            "<div class='d-flex align-items-center mt-2 p-2 border rounded bg-white'>" +
                                    "  <img src='%s' style='width: 40px; height: 40px; object-fit: cover;' class='rounded me-2'>" +
                                    "  <div>" +
                                    "    <div class='fw-bold' style='font-size: 12px;'>%s</div>" +
                                    "    <div class='text-danger' style='font-size: 12px;'>%s</div>" +
                                    "    <a href='/product/detail/%s' class='btn btn-xs btn-primary' style='font-size: 10px; padding: 2px 5px;'>Xem</a>" +
                                    "  </div>" +
                                    "</div>",
                            p.getImage(), p.getName(), price, p.getId()
                    );
                }

                if (count > 3) {
                    botAnswer += "<div class='mt-1 text-center'><a href='/product/list' class='text-muted small'>Xem thêm...</a></div>";
                }
            } else {
                botAnswer = "Hmm, mình không tìm thấy sản phẩm nào tên là '" + message + "'. Bạn thử từ khóa khác xem sao (VD: son, váy, macbook...)";
            }
        }

        response.put("answer", botAnswer);
        return response;
    }
}