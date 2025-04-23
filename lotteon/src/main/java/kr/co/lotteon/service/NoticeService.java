package kr.co.lotteon.service;

import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.repository.NoticeRepository;
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
}
