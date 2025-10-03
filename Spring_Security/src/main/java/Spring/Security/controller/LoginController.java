package Spring.Security.controller;

import Spring.Security.entity.Product;
import Spring.Security.service.ProductServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final ProductServices service;

    // Handler sau khi đăng nhập thành công (nếu bạn cấu hình successForwardUrl)
    @PostMapping("/login_success_handler")
    public String loginSuccessHandler(Model model) {
        System.out.println("Logging user login success...");
        List<Product> listProducts = service.listAll();
        model.addAttribute("listProducts", listProducts);
        return "index";
    }

    // Handler khi đăng nhập thất bại (nếu bạn cấu hình failureForwardUrl)
    @PostMapping("/login_failure_handler")
    public String loginFailureHandler() {
        System.out.println("Login failure handler....");
        return "login";
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Product> listProducts = service.listAll();
        model.addAttribute("listProducts", listProducts);
        return "index";
    }

    @GetMapping("/new")
    public String showNewProductForm(Model model, @ModelAttribute("product") Product product) {
        model.addAttribute("product", product);
        return "new_product";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        service.save(product);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditProductForm(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("edit_product");
        Product product = service.get(id);
        mav.addObject("product", product);
        return mav;
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/";
    }
}
