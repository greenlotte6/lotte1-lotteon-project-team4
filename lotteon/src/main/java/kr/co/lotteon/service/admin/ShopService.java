package kr.co.lotteon.service.admin;

import kr.co.lotteon.dao.ShopMapper;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.dto.ShopDTO;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Shop;
import kr.co.lotteon.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final ModelMapper modelMapper;

    // 글 목록 조회
    public List<ShopDTO> findShopList() {
        List<Shop> shops = shopRepository.findAll();

        return shops.stream()
                .map(shop -> {
                    ShopDTO sDTO = modelMapper.map(shop, ShopDTO.class);

                    sDTO.setCompany(shop.getSeller().getCompany());
                    sDTO.setCeo(shop.getSeller().getCeo());
                    sDTO.setBiz_num(shop.getSeller().getBiz_num());
                    sDTO.setOsn(shop.getSeller().getOsn());
                    sDTO.setNumber(shop.getSeller().getNumber());
                    sDTO.setAid(shop.getSeller().getAid());
                    sDTO.setSeller_aid(shop.getSeller().getAid());

                    log.info("sDTO : {}", sDTO);

                    return sDTO;
                })
                .collect(Collectors.toList());
    }

    public void delete(List<String> seller_aid) {

        shopRepository.deleteAllById(seller_aid);
    }

    /* 글 목록 검색
    public void searchShop(PageRequestDTO pageRequestDTO) {

    }
    */
}
