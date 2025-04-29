package kr.co.lotteon.service;

import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;

    // Qna 객체를 생성하는 메소드
    public void createQna(String userUid, String qnaType1, String qnaType2, String title, String content, String writer) {
        // Qna 엔티티 빌더를 사용하여 Qna 객체 생성
        Qna qna = Qna.builder()
                .userUid(userUid)  // 사용자의 UID
                .qnaType1(qnaType1)
                .qnaType2(qnaType2)
                .title(title)
                .content(content)
                .status("검토중")  // 기본 상태 설정
                .writer(writer)
                .build();

        // Qna 엔티티를 데이터베이스에 저장
        qnaRepository.save(qna);
    }
    public List<Qna> getQnaListByType(String qnaType1) {
        return qnaRepository.findByQnaType1(qnaType1);
    }
}
