package kr.co.lotteon.controller.admin;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.OrdersDTO;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
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
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<OrdersDTO> pageResponseDTO = orderService.findById(pageRequestDTO);
        model.addAttribute(pageResponseDTO);
        return "/admin/order/list";
    }

    @GetMapping("/admin/order/search")
    public String searchList(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<OrdersDTO> pageResponseDTO = orderService.searchList(pageRequestDTO);
        model.addAttribute(pageResponseDTO);
        return "/admin/order/list";
    }

    @GetMapping("/admin/order/delivery")
    public String orderDelivery(HttpSession session) {
        if (session.getAttribute("user") == null) {

            return "redirect:/member/login";
        }

        return "/admin/order/delivery";
    }

}
