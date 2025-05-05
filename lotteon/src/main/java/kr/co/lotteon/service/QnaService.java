package kr.co.lotteon.service;

import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QnaService {

    @Autowired
    private QnaRepository qnaRepository;

    // Qna 객체를 생성하는 메소드
    public void createQna(Users user, String qnaType1, String qnaType2, String title, String content, String writer, LocalDateTime date) {
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

    // qnaType1에 해당하는 Qna 목록 조회 (전체 리스트)
    public List<Qna> getQnaListByType(String qnaType1) {
        return qnaRepository.findByQnaType1(qnaType1);
    }

    // qnaType1에 해당하는 Qna 목록 조회 (페이지네이션 적용)
    public Page<Qna> getQnaListByType(String type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
        return qnaRepository.findByQnaType1(type, pageable);
    }

    // qnaType1 + uid 기준 목록 조회 (페이지네이션 + 최신순)
    public Page<Qna> getQnaListByTypeAndUser(String type, Users uid, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
        return qnaRepository.findByQnaType1AndUserOrderByDateDesc(type, uid, pageable);
    }

    // 전체 Qna 조회
    public List<Qna> getQnaList() {
        return qnaRepository.findAll();
    }

    public List<Qna> getRecentQnas(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date"));
        return qnaRepository.findAll(pageable).getContent();
    }

}
