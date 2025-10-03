package Spring.Security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Nếu bạn đã có Product entity/service thì có thể inject và map qua form.
// Ở đây để sửa lỗi 500 nhanh, mình chỉ cần đảm bảo luôn có "product" trong model.
@Controller
@RequestMapping("/products")
public class ProductPageController {

    @GetMapping({"", "/"})
    public String list(Model model) {
        // model.addAttribute("products", productService.findAll()); // nếu có
        return "index";
    }

    // NEW: luôn add một object "product" cho form tạo mới
    @GetMapping("/new")
    public String newProduct(Model model) {
        if (!model.containsAttribute("product")) {
            model.addAttribute("product", new ProductForm()); // dummy bean cho Thymeleaf binding
        }
        return "new_product";
    }

    // NEW: luôn add một object "product" cho form edit
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        // TODO: nếu có service, load theo id rồi map sang ProductForm
        // ProductForm form = productService.findById(id).map(ProductForm::fromEntity).orElseGet(ProductForm::new);
        // model.addAttribute("product", form);

        if (!model.containsAttribute("product")) {
            ProductForm form = new ProductForm();
            form.setId(id); // đặt sẵn id để th:field vẫn binding được
            model.addAttribute("product", form);
        }
        return "edit_product";
    }

    // --- DTO đơn giản chỉ để Thymeleaf binding, KHÔNG bắt buộc trùng entity ---
    public static class ProductForm {
        private Long id;
        private String name;
        private String brand;
        private String madein;
        private Double price;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getBrand() { return brand; }
        public void setBrand(String brand) { this.brand = brand; }
        public String getMadein() { return madein; }
        public void setMadein(String madein) { this.madein = madein; }
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }

        // Nếu có entity Product thì có thể thêm:
        // public static ProductForm fromEntity(Product p) { ... }
    }
}
