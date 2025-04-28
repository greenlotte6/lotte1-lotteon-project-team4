package kr.co.lotteon.controller.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @GetMapping("/admin/order/list")
    public String list() {
        return "/admin/order/list";
    }

    //@GetMapping("/admin/order/delivery")
    //public String delivery() {
    //    return "/admin/order/delivery";
    //}

    @GetMapping("/admin/order/delivery")
    public String orderDelivery(HttpSession session) {
        if (session.getAttribute("user") == null) {

            return "redirect:/member/login";
        }

        return "/admin/order/delivery";
    }

}
