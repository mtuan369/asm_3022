package com.example.asm.controller.admin;

import com.example.asm.dao.CategoryDAO;
import com.example.asm.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CategoryAController {
    @Autowired
    CategoryDAO dao;

    @RequestMapping("/admin/categories/index")
    public String index(Model model) {
        Category item = new Category();
        model.addAttribute("item", item);
        model.addAttribute("items", dao.findAll());
        return "admin/categories/index";
    }

    @RequestMapping("/admin/categories/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        try {
            Category item = dao.findById(id).get();
            model.addAttribute("item", item);
            model.addAttribute("items", dao.findAll());
        } catch (Exception e) {
            return "redirect:/admin/categories/index";
        }
        return "admin/categories/index";
    }

    // --- SỬA LỖI: THÊM VALIDATE CHO CREATE ---
    @RequestMapping("/admin/categories/create")
    public String create(Model model, Category item) {
        // 1. Kiểm tra tên rỗng
        if (item.getName() == null || item.getName().trim().isEmpty()) {
            model.addAttribute("error", "Vui lòng nhập tên loại hàng!");
            model.addAttribute("items", dao.findAll()); // Load lại danh sách
            return "admin/categories/index"; // Trả về trang cũ để hiện lỗi
        }

        dao.save(item);
        model.addAttribute("message", "Thêm mới thành công!");
        return "redirect:/admin/categories/index";
    }

    // --- SỬA LỖI: THÊM VALIDATE CHO UPDATE (ĐÂY LÀ PHẦN BẠN CẦN) ---
    @RequestMapping("/admin/categories/update")
    public String update(Model model, Category item) {
        // 1. Kiểm tra tên rỗng khi cập nhật
        if (item.getName() == null || item.getName().trim().isEmpty()) {
            model.addAttribute("error", "Tên loại hàng không được để trống!");
            model.addAttribute("items", dao.findAll()); // Load lại danh sách để không bị trắng trang
            return "admin/categories/index"; // Ở lại trang cũ để khách thấy lỗi
        }

        dao.save(item);
        model.addAttribute("message", "Cập nhật thành công!");
        return "redirect:/admin/categories/edit/" + item.getId();
    }

    @RequestMapping("/admin/categories/delete/{id}")
    public String delete(Model model, @PathVariable("id") Integer id) {
        try {
            dao.deleteById(id);
            model.addAttribute("message", "Xóa thành công!");
        } catch (Exception e) {
            // Nếu có lỗi (do ràng buộc khóa ngoại), ta bắt lỗi và gửi thông báo ra view
            model.addAttribute("error", "Không thể xóa loại hàng này vì đang chứa sản phẩm!");
            // Vì đang ở trang delete (thường là redirect), để hiện lỗi ta nên forward hoặc xử lý khác.
            // Tuy nhiên, cách đơn giản nhất ở đây là quay về index và hy vọng user hiểu,
            // hoặc trả về view index kèm error (nhưng URL sẽ là /delete/...)
            // Cách tốt nhất cho redirect: Dùng RedirectAttributes (nhưng để đơn giản mình trả về view)
            model.addAttribute("item", new Category());
            model.addAttribute("items", dao.findAll());
            return "admin/categories/index";
        }
        return "redirect:/admin/categories/index";
    }

    // Nút làm mới
    @RequestMapping("/admin/categories/reset")
    public String reset() {
        return "redirect:/admin/categories/index";
    }
}