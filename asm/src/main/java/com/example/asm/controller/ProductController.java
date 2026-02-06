package com.example.asm.controller;

import com.example.asm.dao.ProductDAO;
import com.example.asm.dao.ReviewDAO;
import com.example.asm.entity.Account;
import com.example.asm.entity.Product;
import com.example.asm.entity.Review;
import com.example.asm.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    ProductDAO dao;

    @Autowired
    ReviewDAO reviewDAO;

    @Autowired
    SessionService session;

    // 1. CHI TIẾT SẢN PHẨM (Giữ nguyên code đã nâng cấp)
    @RequestMapping("/product/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Product item = dao.findById(id).get();
        model.addAttribute("item", item);

        List<Review> reviews = reviewDAO.findByProductId(id);
        model.addAttribute("reviews", reviews);

        List<Product> related = dao.findRelatedProducts(
                item.getCategory().getId(),
                item.getId(),
                PageRequest.of(0, 4)
        );
        model.addAttribute("related", related);

        return "product/detail";
    }

    // 2. DANH SÁCH SẢN PHẨM [CẬP NHẬT LỌC GIÁ]
    @RequestMapping("/product/list")
    public String list(Model model,
                       @RequestParam("cid") Optional<Integer> cid,
                       @RequestParam("keywords") Optional<String> kw,
                       @RequestParam("min") Optional<Double> min, // Mới: Giá thấp nhất
                       @RequestParam("max") Optional<Double> max, // Mới: Giá cao nhất
                       @RequestParam("p") Optional<Integer> p) {

        // Mặc định trang 0, 9 sản phẩm/trang
        Pageable pageable = PageRequest.of(p.orElse(0), 9);
        Page<Product> page;

        // Logic lọc ưu tiên
        if (cid.isPresent()) {
            page = dao.findByCategoryId(cid.get(), pageable);
        } else if (kw.isPresent()) {
            page = dao.findByKeywords("%" + kw.get() + "%", pageable);
        } else if (min.isPresent() && max.isPresent()) {
            // [MỚI] Nếu có min và max thì lọc theo giá
            page = dao.findByPriceBetween(min.get(), max.get(), pageable);
        } else {
            page = dao.findAll(pageable);
        }

        model.addAttribute("items", page);
        return "product/list";
    }

    // 3. XỬ LÝ GỬI ĐÁNH GIÁ (Giữ nguyên)
    @PostMapping("/product/review")
    public String postReview(Model model,
                             @RequestParam("id") Integer productId,
                             @RequestParam("rating") Integer rating,
                             @RequestParam("comment") String comment) {
        Account user = session.get("user");
        if(user == null) return "redirect:/auth/login";

        Review review = new Review();
        review.setProduct(dao.findById(productId).get());
        review.setAccount(user);
        review.setRating(rating);
        review.setComment(comment);
        review.setReviewDate(new java.util.Date());

        reviewDAO.save(review);
        return "redirect:/product/detail/" + productId;
    }
}