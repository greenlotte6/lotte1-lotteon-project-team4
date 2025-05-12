package kr.co.lotteon.controller.admin;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.service.admin.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;

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
