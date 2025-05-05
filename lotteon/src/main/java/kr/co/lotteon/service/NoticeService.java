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

    public List<Notice> getAllNotices() {
        return noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "noticeId"));
    }

    public void saveNotice(Notice notice) {
        noticeRepository.save(notice);
    }

    public Notice findById(int id) {
        return noticeRepository.findById(id).orElseThrow(() -> new RuntimeException("공지사항 없음"));
    }

    public void updateNotice(Notice notice) {
        Notice existing = noticeRepository.findById(notice.getNoticeId())
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 없습니다."));

        existing.setTitle(notice.getTitle());
        existing.setContent(notice.getContent());
        existing.setNoticeType(notice.getNoticeType());

        noticeRepository.save(existing);
    }


    public Notice getNoticeById(int id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다. ID = " + id));
    }

    public Page<Notice> getNoticePage(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    public void deleteNotice(int id) {
        noticeRepository.deleteById(id);
    }

    public Page<Notice> getNoticePageByType(String type, Pageable pageable) {
        return noticeRepository.findByNoticeType(type, pageable);
    }

    public void deleteNoticesByIds(List<Integer> ids) {
        noticeRepository.deleteAllById(ids);
    }

    public List<Notice> getRecentNotices(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "uploadAt"));
        return noticeRepository.findAll(pageable).getContent();
    }



}
