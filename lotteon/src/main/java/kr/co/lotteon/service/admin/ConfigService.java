package kr.co.lotteon.service.admin;

import kr.co.lotteon.dto.TermsDTO;
import kr.co.lotteon.entity.Terms;
import kr.co.lotteon.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConfigService {

    private final ModelMapper modelMapper;
    private final TermsRepository termsRepository;

    public TermsDTO findById(int terms_id) {

        Terms terms = termsRepository.findById(terms_id).orElse(null);

        return modelMapper.map(terms, TermsDTO.class);

    }

    public void policy(TermsDTO termsDTO) {
        Optional<Terms> termsOpt = termsRepository.findById(1);

        // 아이디가 1인 값인 데이터 존재 여부
        if(termsOpt.isPresent()) {

            // 존재하면 데이터 가져오기
            Terms terms = termsOpt.get();

            // 널이 아니면 클라이언트가 보낸 값을 엔티티에 저장
            if(termsDTO.getPrivacyPolicy() != null) {
                terms.setPrivacyPolicy(termsDTO.getPrivacyPolicy());
            }

            if (termsDTO.getLocationTerms() != null) {
                terms.setLocationTerms(termsDTO.getLocationTerms());
            }

            if(termsDTO.getElectronicTerms() != null) {
                terms.setElectronicTerms(termsDTO.getElectronicTerms());
            }

            if(termsDTO.getSellerTerms() != null) {
                terms.setSellerTerms(termsDTO.getSellerTerms());
            }

            if(termsDTO.getPurchaseTerms() != null) {
                terms.setPurchaseTerms(termsDTO.getPurchaseTerms());
            }

            // DB에 저장
            termsRepository.save(terms);
        }

    }

}