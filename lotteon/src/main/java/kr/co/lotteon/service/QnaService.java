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

    // 공통 - Qna 생성 (관리자, 사용자 모두 사용)
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

    // 공통 - Qna 단일 조회 (관리자, 사용자 모두 사용)
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

    // 관리자 - 전체 Qna 목록 조회
    public List<Qna> getQnaList() {
        return qnaRepository.findAll();
    }

    // 관리자 - 최신순 Qna 제한 조회
    public List<Qna> getRecentQnas(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date"));
        return qnaRepository.findAll(pageable).getContent();
    }

    // 관리자 - QnaType1 기준 전체 조회
    public List<Qna> getQnaListByTypeRaw(String qnaType1) {
        return qnaRepository.findByQnaType1(qnaType1);
    }

    // 관리자 - QnaType1 기준 페이지 조회 (DTO 반환)
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

    // 사용자 - QnaType1 + 유저 기준 페이지 조회 (사용자가 본인의 Qna 목록을 조회할 때)
    public Page<Qna> getQnaListByTypeAndUser(String type, Users uid, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
        return qnaRepository.findByQnaType1AndUserOrderByDateDesc(type, uid, pageable);
    }

    // 관리자 - 전체 Qna 페이지 (DTO 변환)
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

    // 관리자 - Qna 답변 등록 + 상태 변경
    public void answerQna(Long qnaId, String answerContent) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Qna가 존재하지 않습니다."));

        qna.setAnswer(answerContent);
        qna.setStatus("답변완료");

        qnaRepository.save(qna);
    }

    // 관리자 - 선택삭제 기능
    public void deleteQnaById(Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Qna가 존재하지 않습니다."));
        qnaRepository.delete(qna);
        log.info("Qna 삭제 완료 - ID: {}", qnaId);
    }

    // 관리자 - 전체 삭제 기능
    public void deleteAllQnas() {
        qnaRepository.deleteAll();
        log.info("전체 Qna 삭제 완료");
    }

    // 관리자 - 여러 Qna 삭제 기능
    public void deleteQnasByIds(List<Long> qnaIds) {
        List<Qna> qnas = qnaRepository.findAllById(qnaIds);
        if (qnas.size() != qnaIds.size()) {
            throw new IllegalArgumentException("삭제할 Qna ID 중 일부가 존재하지 않습니다.");
        }
        qnaRepository.deleteAll(qnas);
        log.info("선택한 Qna들 삭제 완료 - IDs: {}", qnaIds);
    }
}