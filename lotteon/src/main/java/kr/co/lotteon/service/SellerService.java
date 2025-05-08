package kr.co.lotteon.service;

import kr.co.lotteon.dto.SellerDTO;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.entity.Shop;
import kr.co.lotteon.entity.SystemStatus;
import kr.co.lotteon.repository.SellerRepository;
import kr.co.lotteon.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SellerService {


    private final SellerRepository sellerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ShopRepository shopRepository;

    public void saveSeller(SellerDTO dto) {
        Seller seller = Seller.builder()
                .aid(dto.getAid())
                .password(passwordEncoder.encode(dto.getPassword()))
                .company(dto.getCompany())
                .ceo(dto.getCeo())
                .biz_num(dto.getBiz_num())
                .osn(dto.getOsn())
                .number(dto.getNumber())
                .fax(dto.getFax())
                .zip(dto.getZip())
                .addr1(dto.getAddr1())
                .addr2(dto.getAddr2())
                .role("SELLER")
                .build();

        sellerRepository.save(seller);
    }

    public Seller loginSeller(String aid, String password) {
        return sellerRepository.findById(aid)
                .filter(seller -> passwordEncoder.matches(password, seller.getPassword()))
                .orElse(null);
    }

    public long countByAid(String aid) {
        return sellerRepository.countByAid(aid);
    }









}





