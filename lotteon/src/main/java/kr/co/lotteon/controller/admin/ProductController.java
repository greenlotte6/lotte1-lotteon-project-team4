package kr.co.lotteon.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("AdminProductController")
public class ProductController {

    @GetMapping("/admin/product/list")
    public String list() {
        return "/admin/product/list";
    }

    @GetMapping("/admin/product/register")
    public String register() {
        return "/admin/product/register";
    }

}
