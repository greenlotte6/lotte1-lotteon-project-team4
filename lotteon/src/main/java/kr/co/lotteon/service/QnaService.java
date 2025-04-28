package kr.co.lotteon.service;

import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QnaService {
    private final QnaRepository qnaRepository;

    public QnaService(QnaRepository qnaRepository) {
        this.qnaRepository = qnaRepository;
    }

    public void saveQna(Qna qna) {
        qnaRepository.save(qna);
    }
}
