package kr.co.lotteon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestDTO {

    @Builder.Default // 초기화
    private int no = 1;

    @Builder.Default
    private String cate = "free";

    @Builder.Default
    private int pg = 1;

    @Builder.Default
    private int size = 10;

    private String searchType;
    private String keyword;

    private String sortType = "recent"; // 기본값 설정

    // 글 목록 페이징 처리를 위한 Pageable 객체 생성 메서드
    public Pageable getPageable(String sort) { // sort 정렬 문자열, Pageable로 페이징 처리
        return PageRequest.of(this.pg - 1, this.size, Sort.by(sort).descending());
    }

    // 정렬 없이 또는 상품 목록에 사용하는 Pageable
    public Pageable getPageableNotSort() {

        Sort sort;

        switch (sortType) {
            case "best":
                sort = Sort.by(Sort.Direction.DESC, "hits");
                break;
            case "lowPrice":
                sort = Sort.by(Sort.Direction.ASC, "price");
                break;
            case "highPrice":
                sort = Sort.by(Sort.Direction.DESC, "price");
                break;
            case "rating":
                // 평균 평점 정렬
                sort = Sort.by(Sort.Direction.DESC, "rating");
                break;
            case "review":
                // 리뷰 수 정렬
                sort = Sort.by(Sort.Direction.DESC, "review_count");
                break;
            case "recent":
            default:
                sort = Sort.by(Sort.Direction.DESC, "pid");
                break;
        }

        return PageRequest.of(this.pg - 1, this.size, sort);

    }
}
