package kr.co.lotteon.repository;

import kr.co.lotteon.entity.OAuth2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuth2Repository extends JpaRepository<OAuth2, String> {

    OAuth2 findByName(String name);

}
