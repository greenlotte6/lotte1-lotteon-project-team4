package kr.co.lotteon.service;

import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;

    public void saveQna(QnaDTO dto) {
        Qna qna = Qna.builder()
                .userUid(dto.getUserUid())
                .qnaType1(dto.getQnaType1())
                .qnaType2(dto.getQnaType2())
                .title(dto.getTitle())
                .content(dto.getContent())
                .status("대기")  // 기본값
                .build();

        qnaRepository.save(qna);
    }

    // 카테고리별로 문의 내용 조회
    public List<Qna> findByCategory(String category) {
        return qnaRepository.findByQnaType1(category);
    }
}