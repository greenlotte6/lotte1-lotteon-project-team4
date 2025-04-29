package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    List<Users> findByUidContaining(String keyword);
    List<Users> findByUnameContaining(String keyword);
    List<Users> findByEmailContaining(String keyword);
    List<Users> findByHpContaining(String keyword);
    Optional<Users> findByUnameAndEmail(String uname, String email);


    Optional<Users> findByUid(String uid);
    int countByUid(String uid);
    int countByEmail(String email);
    int countByHp(String hp);
//    @Query("SELECT u FROM Users u WHERE u.status = :status")
//    List<Users> findByStatus(@Param("status") String status);

}
