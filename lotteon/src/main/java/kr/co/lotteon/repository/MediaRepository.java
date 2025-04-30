package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MediaRepository extends JpaRepository<Media,Integer> {

}
