package kr.co.lotteon.repository;

import jakarta.persistence.EntityManager;
import kr.co.lotteon.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    boolean existsByEmail(String email);



//    @Query("SELECT u FROM Users u WHERE u.status = :status")
//    List<Users> findByStatus(@Param("status") String status);
}
