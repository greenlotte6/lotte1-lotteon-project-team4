package kr.co.lotteon.service;

import kr.co.lotteon.dto.SellerDTO;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SellerService {


    private final SellerRepository sellerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void saveSeller(SellerDTO dto) {
        Seller seller = Seller.builder()
                .aid(dto.getAid())
                .password1(passwordEncoder.encode(dto.getPassword1()))
                .password2(passwordEncoder.encode(dto.getPassword2()))
                .company(dto.getCompany())
                .ceo(dto.getCeo())
                .biz_num(dto.getBiz_num())
                .osn(dto.getOsn())
                .number(dto.getNumber())
                .fax(dto.getFax())
                .addr1(dto.getAddr1())
                .addr2(dto.getAddr2())
                .role("SELLER")
                .build();

        sellerRepository.save(seller);
    }

    public Seller loginSeller(String aid, String password) {
        return sellerRepository.findById(aid)
                .filter(seller -> passwordEncoder.matches(password, seller.getPassword1()))
                .orElse(null);
    }

    public long countByAid(String aid) {
        return sellerRepository.countByAid(aid);
    }




}
