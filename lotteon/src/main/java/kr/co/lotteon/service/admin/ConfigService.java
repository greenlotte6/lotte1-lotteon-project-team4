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

    public List<TermsDTO> findAll() {

        List<Terms> termsList = termsRepository.findAll();

        return termsList.stream()
                .map(terms -> (modelMapper.map(terms, TermsDTO.class)))
                .collect(Collectors.toList());

    }

//    public void modifyPolicy(Terms terms) {
//        // 해당 terms_id를 가진 엔티티가 존재하는지 확인
//        Terms existingTerms = termsRepository.findById(terms.getTerms_id())
//                .orElseThrow(() -> new IllegalArgumentException("해당 약관이 존재하지 않습니다."));
//
//        // 엔티티 필드를 수정
//        existingTerms.setPurchase_terms(terms.getPurchase_terms());
//        existingTerms.setSeller_terms(terms.getSeller_terms());
//        existingTerms.setElectronic_terms(terms.getElectronic_terms());
//        existingTerms.setLocation_terms(terms.getLocation_terms());
//        existingTerms.setPrivacy_policy(terms.getPrivacy_policy());
//
//        // 엔티티를 저장
//        termsRepository.save(existingTerms);  // 수정된 엔티티 저장
//    }

}