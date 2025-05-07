package kr.co.lotteon.service;

import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QnaService {

    @Autowired
    private final QnaRepository qnaRepository;

    // Qna 생성
    public void createQna(Users user, String qnaType1, String qnaType2,
                          String title, String content, String writer, LocalDateTime date) {
        Qna qna = Qna.builder()
                .user(user)
                .qnaType1(qnaType1)
                .qnaType2(qnaType2)
                .title(title)
                .content(content)
                .date(date)
                .writer(writer)
                .status("검토중")
                .build();

        log.info(user.getUid());
        qnaRepository.save(qna);
    }

    // 전체 Qna 목록
    public List<Qna> getQnaList() {
        return qnaRepository.findAll();
    }

    // 최신순 Qna 제한 조회
    public List<Qna> getRecentQnas(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date"));
        return qnaRepository.findAll(pageable).getContent();
    }

    // QnaType1 기준 전체 조회
    public List<Qna> getQnaListByTypeRaw(String qnaType1) {
        return qnaRepository.findByQnaType1(qnaType1);
    }

    // ✅ QnaType1 기준 페이지 조회 (DTO 반환)
    public Page<QnaDTO> getQnaListByType(String type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
        Page<Qna> qnaPage = qnaRepository.findByQnaType1(type, pageable);

        return qnaPage.map(qna -> QnaDTO.builder()
                .qnaid(qna.getQnaId())
                .uid(qna.getUser().getUid())
                .qnaType1(qna.getQnaType1())
                .qnaType2(qna.getQnaType2())
                .title(qna.getTitle())
                .content(qna.getContent())
                .date(qna.getDate())
                .status(qna.getStatus())
                .answer(qna.getAnswer())
                .writer(qna.getWriter())
                .build());
    }

    // QnaType1 + 유저 기준 페이지 조회
    public Page<Qna> getQnaListByTypeAndUser(String type, Users uid, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
        return qnaRepository.findByQnaType1AndUserOrderByDateDesc(type, uid, pageable);
    }

    // 전체 Qna 페이지 (DTO 변환)
    public Page<QnaDTO> getQnaPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
        Page<Qna> qnaPage = qnaRepository.findAll(pageable);

        return qnaPage.map(qna -> QnaDTO.builder()
                .qnaid(qna.getQnaId())
                .uid(qna.getUser().getUid())
                .qnaType1(qna.getQnaType1())
                .qnaType2(qna.getQnaType2())
                .title(qna.getTitle())
                .content(qna.getContent())
                .date(qna.getDate())
                .status(qna.getStatus())
                .answer(qna.getAnswer())
                .writer(qna.getWriter())
                .build());
    }

    // Qna 단일 조회 (DTO 반환용)
    public QnaDTO getQnaById(Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Qna가 존재하지 않습니다."));

        return QnaDTO.builder()
                .qnaid(qna.getQnaId())
                .uid(qna.getUser().getUid())
                .qnaType1(qna.getQnaType1())
                .qnaType2(qna.getQnaType2())
                .title(qna.getTitle())
                .content(qna.getContent())
                .date(qna.getDate())
                .status(qna.getStatus())
                .answer(qna.getAnswer())
                .writer(qna.getWriter())
                .build();
    }

    // Qna 답변 등록 + 상태 변경
    public void answerQna(Long qnaId, String answerContent) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Qna가 존재하지 않습니다."));

        qna.setAnswer(answerContent);
        qna.setStatus("답변완료");

        qnaRepository.save(qna);
    }
}
