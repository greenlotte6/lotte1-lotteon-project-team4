package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VersionRepository extends JpaRepository<Version, Integer> {
}