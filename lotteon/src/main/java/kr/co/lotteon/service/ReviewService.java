package kr.co.lotteon.service;


import kr.co.lotteon.dto.ReviewDTO;
import kr.co.lotteon.entity.Review;
import kr.co.lotteon.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void saveReview(ReviewDTO reviewDTO, MultipartFile[] files) {

        Review review = Review.builder()
                .productsPid(reviewDTO.getProductsPid())
                .usersUid(reviewDTO.getUsersUid())
                .rating(reviewDTO.getRating())
                .comment(reviewDTO.getComment())
                .writeAt(reviewDTO.getWriteAt())
                .build();

        reviewRepository.save(review);
    }
}
