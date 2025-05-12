package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.entity.SystemStatus;
import kr.co.lotteon.repository.SellerRepository;
import kr.co.lotteon.service.SellerService;
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
    private final SellerService sellerService;
    private final SellerRepository sellerRepository;

    @GetMapping("/admin/shop/list")
    public String list(Model model, PageRequestDTO pageRequestDTO) {

        if (pageRequestDTO.getPg() <= 0) {
            pageRequestDTO.setPg(1);
        }

        PageResponseDTO<ShopDTO> pageResponseDTO = shopService.findShopList(pageRequestDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO); //

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
    public String deleteShops(@RequestParam(name = "seller_aids", required = false) List<String> aids) {
        if (aids != null && !aids.isEmpty()) {
            sellerService.deleteSellersByIds(aids); // 서비스에 삭제 로직 구현
        }
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


    @PostMapping("/admin/shop/toggleMgmt")
    public String toggleMgmt(@RequestParam String aid,
                             @RequestParam String newStatus) {
        System.out.println("== TOGGLE MGMT ==");
        System.out.println("aid = " + aid);
        System.out.println("newStatus = " + newStatus);

        SystemStatus status = SystemStatus.valueOf(newStatus);
        sellerService.updateStatus(aid, status);
        return "redirect:/admin/shop/list";
    }


}
