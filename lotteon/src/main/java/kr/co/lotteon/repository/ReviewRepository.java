package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Review;
import kr.co.lotteon.repository.custom.ReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Integer>, ReviewRepositoryCustom {


}
