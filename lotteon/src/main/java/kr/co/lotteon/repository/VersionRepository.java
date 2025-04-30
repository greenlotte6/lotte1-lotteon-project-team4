package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VersionRepository extends JpaRepository<Version, Integer> {
}