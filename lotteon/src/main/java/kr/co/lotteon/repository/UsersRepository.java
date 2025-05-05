package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.custom.UserRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String>, UserRepositoryCustom {
    List<Users> findByUidContaining(String keyword);
    List<Users> findByUnameContaining(String keyword);
    List<Users> findByEmailContaining(String keyword);
    List<Users> findByHpContaining(String keyword);
    Optional<Users> findByUnameAndEmail(String uname, String email);
    Optional<Users> findByUidAndEmail(String uid, String email);

    Optional<Users> findByUid(String uid);
    int countByUid(String uid);
    int countByEmail(String email);
    int countByHp(String hp);

    // 회원 목록 검색 및 페이징 처리
    Page<Users> findByUidContaining(String keyword, Pageable pageable);
    Page<Users> findByUnameContaining(String keyword, Pageable pageable);
    Page<Users> findByEmailContaining(String keyword, Pageable pageable);
    Page<Users> findByHpContaining(String keyword, Pageable pageable);
    Page<Users> findByUidContainingAndHpContaining(Users uid, String hp, Pageable pageable);

    String uid(String uid);


    //    @Query("SELECT u FROM Users u WHERE u.status = :status")
//    List<Users> findByStatus(@Param("status") String status);

}
