package kr.co.lotteon.repository;

import kr.co.lotteon.entity.CompanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, Integer> {
}
