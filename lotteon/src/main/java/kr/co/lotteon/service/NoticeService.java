package kr.co.lotteon.service;

import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.repository.NoticeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    // 공통: 모든 공지사항 조회
    public List<Notice> getAllNotices() {
        return noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "noticeId"));
    }

    // 공통: 공지사항 상세 조회
    public Notice getNoticeById(int id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다. ID = " + id));
    }

    // 관리자: 공지사항 조회 (예외 처리 추가)
    public Notice findById(int id) {
        return noticeRepository.findById(id).orElseThrow(() -> new RuntimeException("공지사항 없음"));
    }

    // 공통: 공지사항 페이지 조회
    public Page<Notice> getNoticePage(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    // 공통: 특정 타입의 공지사항 페이지 조회
    public Page<Notice> getNoticePageByType(String type, Pageable pageable) {
        return noticeRepository.findByNoticeType(type, pageable);
    }

    // 공통: 최신 공지사항 조회
    public List<Notice> getRecentNotices(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "uploadAt"));
        return noticeRepository.findAll(pageable).getContent();
    }

    // 관리자: 공지사항 저장
    public void saveNotice(Notice notice) {
        noticeRepository.save(notice);
    }

    // 관리자: 공지사항 수정
    public void updateNotice(Notice notice) {
        Notice existing = noticeRepository.findById(notice.getNoticeId())
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 없습니다."));

        existing.setTitle(notice.getTitle());
        existing.setContent(notice.getContent());
        existing.setNoticeType(notice.getNoticeType());

        noticeRepository.save(existing);
    }

    // 관리자: 공지사항 삭제
    public void deleteNotice(int id) {
        noticeRepository.deleteById(id);
    }

    // 관리자: 여러 공지사항 삭제
    public void deleteNoticesByIds(List<Integer> ids) {
        noticeRepository.deleteAllById(ids);
    }

    // 관리자: 특정 타입의 공지사항 조회
    public Page<Notice> findByNoticeType(Pageable pageable, String type) {
        return noticeRepository.findByNoticeType(type, pageable);
    }
}
