package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Copyright;
import kr.co.lotteon.entity.Support;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportRepository extends JpaRepository<Support, Integer> {
}
