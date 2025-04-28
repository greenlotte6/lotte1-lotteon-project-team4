package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.ShopDTO;
import kr.co.lotteon.service.admin.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/admin/shop/list")
    public String list(Model model) {

        List<ShopDTO> shopList = shopService.findShopList();

        log.info("shops : {}", shopList);

        model.addAttribute("shopList", shopList);

        return "/admin/shop/list";
    }

    @GetMapping("/admin/shop/sales")
    public String sales() {
        return "/admin/shop/sales";
    }

    @PostMapping("/admin/shop/delete")
    public String delete(@RequestParam("seller_aid") List<String> seller_aid) {
        shopService.delete(seller_aid);

        return "redirect:/admin/shop/list";
    }

}
