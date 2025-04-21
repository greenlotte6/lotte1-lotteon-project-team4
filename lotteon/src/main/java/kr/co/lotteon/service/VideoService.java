package kr.co.lotteon.service;


import kr.co.lotteon.entity.VideoEntity;
import kr.co.lotteon.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    // ✅ 오버로딩 메서드 추가
    public Page<VideoEntity> getVideoPage(int page, int pageSize) {
        return videoRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    // 기존 정의된 메서드는 추후 정렬/필터링용으로 활용 가능
    public Page<VideoEntity> getVideoPage(int page, int pageSize, String sortBy, String order, String filter, String filterValue) {
        return videoRepository.findAll(PageRequest.of(page - 1, pageSize));
    }
}