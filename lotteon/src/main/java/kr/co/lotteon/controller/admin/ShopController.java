package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.service.admin.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/admin/shop/list")
    public String list(Model model, PageRequestDTO pageRequestDTO) {

        if (pageRequestDTO.getPg() <= 0) {
            pageRequestDTO.setPg(1);
        }

        PageResponseDTO<ShopDTO> pageResponseDTO = shopService.findShopList(pageRequestDTO);

        model.addAttribute(pageResponseDTO);

        return "/admin/shop/list";
    }

    @GetMapping("/admin/shop/sales")
    public String sales(PageRequestDTO pageRequestDTO, Model model) {

        PageResponseDTO<SalesDTO> pageResponseDTO = shopService.salesList(pageRequestDTO);

        log.info("pageResponseDTO@@@@@@@@@: {}", pageResponseDTO);
        log.info("pageResponseDTO@@@@@@@@@: {}", pageResponseDTO);
        log.info("pageResponseDTO@@@@@@@@@: {}", pageResponseDTO);
        log.info("pageResponseDTO@@@@@@@@@: {}", pageResponseDTO);

        model.addAttribute(pageResponseDTO);

        return "/admin/shop/sales";
    }

    @PostMapping("/admin/shop/delete")
    public String delete(@RequestParam("seller_aid") List<String> seller_aid) {
        shopService.delete(seller_aid);

        return "redirect:/admin/shop/list";
    }

    @GetMapping("/admin/shop/searchShop")
    public String searchShop(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<ShopDTO> pageResponseDTO = shopService.searchShop(pageRequestDTO);

        model.addAttribute(pageResponseDTO);

        return "/admin/shop/list";
    }

    @PostMapping("/admin/shop/registerShop")
    public String registerShop(SellerDTO sellerDTO) {
        shopService.registerShop(sellerDTO);
        return "redirect:/admin/shop/list";
    }

}
