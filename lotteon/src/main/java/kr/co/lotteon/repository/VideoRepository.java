package kr.co.lotteon.repository;

import kr.co.lotteon.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<VideoEntity, Integer> {

}
