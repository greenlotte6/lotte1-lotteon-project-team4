package kr.co.lotteon.service;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.entity.Products;
import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.repository.NoticeRepository;
import kr.co.lotteon.repository.ProductRepository;
import kr.co.lotteon.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MainService {

    private final NoticeRepository noticeRepository;
    private final QnaRepository qnaRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    // 메인 화면 (유아동, 럭셔리, 여성패션)
    public List<ProductDTO> mainView(int count) {

        List<Products> productsList = productRepository.findAll();

        // 리스트 섞기
        Collections.shuffle(productsList);

        // 원하는 개수만큼 자르기
        List<Products> selected = productsList.stream()
                .limit(count)
                .collect(Collectors.toList());

        // DTO 변환
        return selected.stream().map(product -> {
            ProductDTO dto = modelMapper.map(product, ProductDTO.class);
            dto.setDiscountPrice(dto.getDiscountedPrice());
            return dto;
        }).collect(Collectors.toList());

//        List<Products> productsList = productRepository.findAll();
//
//        return productsList.stream().map(products -> {
//            ProductDTO productDTO = modelMapper.map(products, ProductDTO.class);
//            productDTO.setPid(products.getPid());
//            productDTO.setDiscountPrice(productDTO.getDiscountedPrice());
//            return productDTO;
//        }).collect(Collectors.toList());

    }

    // 어드민 메인 공지사항 불러오기
    public List<NoticeDTO> findAll() {
        List<Notice> noticeList = noticeRepository.findAll();

        return noticeList.stream()
                .map(notice -> modelMapper.map(notice, NoticeDTO.class))
                .collect(Collectors.toList());
    }

    public List<QnaDTO> findAllQna() {

        List<Qna> qnaList = qnaRepository.findAll();

        return qnaList.stream()
                .map(qna -> {
                    QnaDTO qnaDTO = modelMapper.map(qna, QnaDTO.class);

                    qnaDTO.setUid(qna.getUser().getUid());

                    return qnaDTO;
                })
                .collect(Collectors.toList());

    }
}
