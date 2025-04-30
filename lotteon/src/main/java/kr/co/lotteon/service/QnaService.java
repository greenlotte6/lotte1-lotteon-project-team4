package kr.co.lotteon.service;

import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaService {



    // Qna 객체를 생성하는 메소드
    public void createQna(String userUid, String qnaType1, String qnaType2, String title, String content, String writer, LocalDateTime date) {
        // Qna 엔티티 빌더를 사용하여 Qna 객체 생성
        Qna qna = Qna.builder()
                .userUid(userUid)  // 사용자의 UID
                .qnaType1(qnaType1)
                .qnaType2(qnaType2)
                .title(title)
                .content(content)
                .date(date)
                .writer(writer)
                .status("검토중")
                .build();

        // Qna 엔티티를 데이터베이스에 저장
        qnaRepository.save(qna);
    }

    // qnaType1에 해당하는 Qna 목록을 조회
    public List<Qna> getQnaListByType(String qnaType1) {
        return qnaRepository.findByQnaType1(qnaType1);
    }

    // 페이지네이션을 적용한 Qna 목록 조회
    public Page<Qna> getQnaListByType(String type, Pageable pageable) {
        // "쿠폰/혜택/이벤트" 타입에 해당하는 Qna 리스트를 Pageable 방식으로 반환
        return qnaRepository.findByQnaType1(type, pageable);
    }
    public Page<Qna> getQnaListByTypeAndUser(String type, String userUid, Pageable pageable) {
        return qnaRepository.findByQnaType1AndUserUidOrderByDateDesc(type, userUid, pageable);
    }
    @Autowired
    private QnaRepository qnaRepository;

    public List<Qna> getQnaList() {
        return qnaRepository.findAll();  // 모든 Qna 데이터를 가져옵니다.
    }

}


