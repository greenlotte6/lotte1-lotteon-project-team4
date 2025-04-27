package kr.co.lotteon.repository;

import kr.co.lotteon.dto.PointDTO;
import kr.co.lotteon.entity.Point;
import kr.co.lotteon.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Integer> {

    List<Point> findByUsersUidContaining(String usersUid);
    List<Point> findByUsersUnameContaining(String usersUname);
    List<Point> findByUsersEmailContaining(String usersEmail);
    List<Point> findByUsersHpContaining(String usersHp);

//    @Query("select p.no, " +
//            "    u.uid, " +
//            "    u.uname, " +
//            "    p.given, " +
//            "    p.balance, " +
//            "    p.details, " +
//            "    p.given_date " +
//            "from Point p " +
//            "join p.users u ")
//    public List<Point> UserJoinPointAll();

}
