package kr.co.lotteon.service;

import kr.co.lotteon.dto.TermsDTO;
import kr.co.lotteon.entity.Terms;
import kr.co.lotteon.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyService {

    private final TermsRepository termsRepository;
    private final ModelMapper modelMapper;

    public TermsDTO getTermsSet(int termsId) {
        return termsRepository.findById(termsId)
                .map(terms -> modelMapper.map(terms, TermsDTO.class))
                .orElse(TermsDTO.builder()
                        .purchaseTerms("내용 없음")
                        .sellerTerms("내용 없음")
                        .electronicTerms("내용 없음")
                        .locationTerms("내용 없음")
                        .privacyPolicy("내용 없음")
                        .build());
    }

}
