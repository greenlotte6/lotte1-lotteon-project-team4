package kr.co.lotteon.service.admin;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dao.ShopMapper;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.Sales;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.entity.Shop;
import kr.co.lotteon.entity.SystemStatus;
import kr.co.lotteon.repository.SalesRepository;
import kr.co.lotteon.repository.SellerRepository;
import kr.co.lotteon.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShopService {

    private final SalesRepository salesRepository;
    private final ShopRepository shopRepository;
    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;

    // 상점 목록 조회
    public PageResponseDTO<ShopDTO> findShopList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("aid");

        Page<Seller> pageSeller = sellerRepository.findAll(pageable);

        List<ShopDTO> dtoList = pageSeller.getContent().stream()
                .map(ShopDTO::new)
                .collect(Collectors.toList());

        return PageResponseDTO.<ShopDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) pageSeller.getTotalElements())
                .build();
    }

    // 상점 목록 검색
    public PageResponseDTO<ShopDTO> searchShop(PageRequestDTO pageRequestDTO) {

        // 페이징 처리를 위한 pageable 객체 생성
        Pageable pageable = pageRequestDTO.getPageable("aid"); // 또는 "company"
        Page<Seller> pageSeller = sellerRepository.findAll(pageable);

        Page<Tuple> pageShop = shopRepository.searchShop(pageRequestDTO, pageable);

        List<ShopDTO> shopDTOList = pageShop.getContent().stream().map(tuple -> {

            Shop shop = tuple.get(0, Shop.class);
            String company = tuple.get(1, String.class);
            String ceo = tuple.get(2, String.class);
            String biz_num = tuple.get(3, String.class);
            String osn = tuple.get(4, String.class);
            String number = tuple.get(5, String.class);

            ShopDTO shopDTO = modelMapper.map(shop, ShopDTO.class);
            shopDTO.setCompany(company);
            shopDTO.setCeo(ceo);
            shopDTO.setBiz_num(biz_num);
            shopDTO.setOsn(osn);
            shopDTO.setNumber(number);

            SystemStatus status = shopDTO.getStatus();

            if (status != null) {
                switch (status) {
                    case OPERATING:
                        shopDTO.setOperationText("[운영중]");
                        shopDTO.setStatusClass("green");
                        break;
                    case READY:
                        shopDTO.setOperationText("[운영준비]");
                        shopDTO.setStatusClass("blue");
                        break;
                    case STOPPED:
                        shopDTO.setOperationText("[운영중지]");
                        shopDTO.setStatusClass("red");
                        break;
                }
            } else {
                shopDTO.setOperationText("[운영준비]");
                shopDTO.setStatusClass("blue");
            }

            return shopDTO;
        }).toList();

        int total = (int) pageShop.getTotalElements();

        log.info("total {}", total);

        return PageResponseDTO.<ShopDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(shopDTOList)
                .total(total)
                .build();
    }



    // 상점 등록
    public void registerShop(SellerDTO sellerDTO) {
        if (sellerDTO.getStatus() == null) {
            sellerDTO.setStatus(SystemStatus.READY); // 기본 설정
        }
        Optional<Seller> optSeller = sellerRepository.findById(sellerDTO.getAid());

        log.info("AID {}", sellerDTO.getAid());

        Seller seller;
        if (optSeller.isPresent()) {
            seller = optSeller.get();

            seller.setAid(sellerDTO.getAid());
            seller.setPassword(sellerDTO.getPassword());
            seller.setCompany(sellerDTO.getCompany());
            seller.setCeo(sellerDTO.getCeo());
            seller.setBiz_num(sellerDTO.getBiz_num());
            seller.setOsn(sellerDTO.getOsn());
            seller.setNumber(sellerDTO.getNumber());
            seller.setFax(sellerDTO.getFax());
            seller.setAddr1(sellerDTO.getAddr1());
            seller.setAddr2(sellerDTO.getAddr2());
            String role = sellerDTO.getRole();
            if(role == null) {
                role = "SELLER";
            }
            seller.setRole(role);

            if (sellerDTO.getStatus() == null) {
                seller.setStatus(SystemStatus.READY); // 기본 상태
            } else {
                seller.setStatus(sellerDTO.getStatus());
            }

            log.info("seller {}", seller);

        }else {
            // 상점이 없다면 새로운 상점 등록
            seller = new Seller();
            seller.setAid(sellerDTO.getAid());
            seller.setPassword(sellerDTO.getPassword());
            seller.setCompany(sellerDTO.getCompany());
            seller.setCeo(sellerDTO.getCeo());
            seller.setBiz_num(sellerDTO.getBiz_num());
            seller.setOsn(sellerDTO.getOsn());
            seller.setNumber(sellerDTO.getNumber());
            seller.setFax(sellerDTO.getFax());
            seller.setAddr1(sellerDTO.getAddr1());
            seller.setAddr2(sellerDTO.getAddr2());
            String role = sellerDTO.getRole();
            if(role == null) {
                role = "SELLER";
            }
            seller.setRole(role);

            log.info("seller {}", seller);
        }
        sellerRepository.save(seller);


    }

    public void delete(List<String> seller_aid) {

        shopRepository.deleteAllById(seller_aid);
    }

    // 매출 현황 목록 조회
    public PageResponseDTO<SalesDTO> salesList(PageRequestDTO pageRequestDTO) {

//        Pageable pageable = pageRequestDTO.getPageable("no");
//
//        Page<Tuple> salesPage = salesRepository.salesList(pageable);
//
//        List<SalesDTO> salesDTOList = salesPage.stream().map(tuple -> {
//            Date order_date = tuple.get(0, Date.class);
//            String order_item_id = tuple.get(1, String.class);
//            Integer order_status = tuple.get(2, Integer.class);
//            Integer shipping_status = tuple.get(3, Integer.class);
//            Integer order_total = tuple.get(4, Integer.class);
//            Integer order_total_sum = tuple.get(5, Integer.class);
//
//            SalesDTO salesDTO = modelMapper.map(tuple.get(0, SalesDTO.class), SalesDTO.class);
//            salesDTO.setOrder_date(order_date);
//            salesDTO.setOrder_item_id(order_item_id);
//            salesDTO.setOrder_status(order_status);
//            salesDTO.setOrder_total(order_total);
//            salesDTO.setOrder_total_sum(order_total_sum);
//            return salesDTO;
//
//
//        })


        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<Tuple> salesPage = salesRepository.salesList(pageable);

        List<SalesDTO> salesDTOList = salesPage.stream().map(tuple -> {
            Sales sales = tuple.get(0, Sales.class);
            String company = tuple.get(1, String.class);
            String biz_num = tuple.get(2, String.class);
            String shipping_status = tuple.get(3, String.class);

            SalesDTO salesDTO = modelMapper.map(sales, SalesDTO.class);
            salesDTO.setCompany(company);
            salesDTO.setBiz_num(biz_num);
            salesDTO.setShipping_status(shipping_status);

            return salesDTO;
        }).toList();

        log.info("salesDTOList: {}", salesDTOList);

        int total = (int) salesPage.getTotalElements();

        return PageResponseDTO.<SalesDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(salesDTOList)
                .total(total)
                .build();

    }

    public void toggleMgmtStatus(String aid) {
        Optional<Seller> optSeller = sellerRepository.findById(aid);
        optSeller.ifPresent(seller -> {
            String newStatus = "중단".equals(seller.getMgmt()) ? "승인" : "중단";
            seller.setMgmt(newStatus);
            sellerRepository.save(seller);
        });
    }

    public void toggleStatus(String aid) {
        log.info("toggleStatus() 메서드 실행 - aid: {}", aid);
        Optional<Seller> sellerOpt = sellerRepository.findById(aid);
        if (sellerOpt.isPresent()) {
            Seller seller = sellerOpt.get();
            log.info("조회된 Seller 정보: {}", seller);
            SystemStatus currentStatus = seller.getStatus();
            log.info("현재 상태: {}", currentStatus);
            SystemStatus newStatus;
            String operationText;
            String statusClass;

            if (currentStatus == null || currentStatus == SystemStatus.READY || currentStatus == SystemStatus.STOPPED) {
                newStatus = SystemStatus.OPERATING;
                operationText = "[운영중]";
                statusClass = "green";
            } else {
                newStatus = SystemStatus.STOPPED;
                operationText = "[운영중지]";
                statusClass = "red";
            }
            seller.setStatus(newStatus);
            seller.setOperationText(operationText);
            seller.setStatusClass(statusClass);
            sellerRepository.save(seller);
            log.info("상태 변경 완료 - aid: {}, 새로운 상태: {}, operationText: {}, statusClass: {}", aid, newStatus, operationText, statusClass);
        } else {
            log.warn("toggleStatus() - aid에 해당하는 Seller를 찾을 수 없습니다: {}", aid);
        }
        log.info("toggleStatus() 메서드 종료 - aid: {}", aid);
    }


}
