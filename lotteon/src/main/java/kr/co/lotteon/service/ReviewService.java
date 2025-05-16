package kr.co.lotteon.service;


import kr.co.lotteon.dto.ReviewDTO;
import kr.co.lotteon.entity.Products;
import kr.co.lotteon.entity.Review;
import kr.co.lotteon.repository.ProductRepository;
import kr.co.lotteon.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public void saveReview(ReviewDTO reviewDTO, MultipartFile[] files) {

        Optional<Products> optProduct = productRepository.findById(reviewDTO.getProducts_pid());

        if(optProduct.isPresent()) {
            Products products = optProduct.get();

            Review review = Review.builder()
                    .products(products)
                    .Users_uid(reviewDTO.getUsers_uid())
                    .rating(reviewDTO.getRating())
                    .comment(reviewDTO.getComment())
                    .write_at(reviewDTO.getWrite_at())
                    .build();

            reviewRepository.save(review);

        }


    }
}
