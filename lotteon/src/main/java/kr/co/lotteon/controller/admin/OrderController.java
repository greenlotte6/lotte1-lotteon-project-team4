package kr.co.lotteon.controller.admin;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.OrdersDTO;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.entity.Orders;
import kr.co.lotteon.service.admin.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;

    // 주문 현황 조회
    @GetMapping("/admin/order/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<OrdersDTO> pageResponseDTO = orderService.findById(pageRequestDTO);
        model.addAttribute(pageResponseDTO);
        return "/admin/order/list";
    }

    // 주문 현황 검색
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

    // 배송 정보 입력 데이터 불러오기
    @GetMapping("/admin/order/deliveryDetail")
    @ResponseBody
    public OrdersDTO deliveryDetail(@RequestParam("oid") int oid) {
        return orderService.deliveryDetail(oid);
    }

    // 배송 정보 등록하기
    @PostMapping("/admin/order/deliveryDetail")
    public String modifyDelivery(@RequestParam("oid") int oid,
                                 @RequestParam("delivery_company") String delivery_company,
                                 @RequestParam("delivery_num") String delivery_num,
                                 Model model) {
        OrdersDTO ordersDTO = orderService.modifyDelivery(oid, delivery_company, delivery_num);
        model.addAttribute("ordersDTO", ordersDTO);
        return "redirect:/admin/order/list";
    }

    // 주문상세 보기
    @GetMapping("/admin/order/orderDetail")
    @ResponseBody
    public OrdersDTO orderDetail(@RequestParam("oid") int oid) {
        return orderService.orderDetail(oid);

    }

}
