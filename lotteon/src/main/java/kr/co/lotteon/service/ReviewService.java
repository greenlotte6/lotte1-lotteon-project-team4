package kr.co.lotteon.service;


import kr.co.lotteon.dto.ReviewDTO;
import kr.co.lotteon.entity.Products;
import kr.co.lotteon.entity.Review;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.ProductRepository;
import kr.co.lotteon.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public void saveReview(ReviewDTO reviewDTO, UserDetails files) {

        Products products = Products.builder()
                .pid(reviewDTO.getProducts_pid())
                .build();

        Users user = Users.builder()
                .uid(reviewDTO.getUsers_uid())
                .build();

        Review review = modelMapper.map(reviewDTO, Review.class);

        review.setUsers(user);
        review.setProducts(products);
        reviewRepository.save(review);




    }
}
