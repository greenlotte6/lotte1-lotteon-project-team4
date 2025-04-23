package kr.co.lotteon.service.admin;

import kr.co.lotteon.dto.TermsDTO;
import kr.co.lotteon.entity.Terms;
import kr.co.lotteon.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

}
