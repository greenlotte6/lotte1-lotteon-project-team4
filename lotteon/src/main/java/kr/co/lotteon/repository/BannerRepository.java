package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Integer> {
    List<Banner> findByPositionAndActive(String position, String active);
    List<Banner> findByActive(String active);

}
