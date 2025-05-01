package kr.co.lotteon.service;

import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.repository.NoticeRepository;
import kr.co.lotteon.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MainService {

    private final NoticeRepository noticeRepository;
    private final QnaRepository qnaRepository;
    private final ModelMapper modelMapper;

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
